package org.zly.blogbackjava.service.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import org.zly.blogbackjava.dto.miniapp.response.MiniappLoginResult;
import org.zly.blogbackjava.dto.miniapp.response.WechatSessionResponse;
import org.zly.blogbackjava.entity.WxUser;
import org.zly.blogbackjava.exception.BusinessException;
import org.zly.blogbackjava.repository.MiniappUserRepository;
import org.zly.blogbackjava.service.MiniappService;
import org.zly.blogbackjava.util.IdGenerator;
import org.zly.blogbackjava.util.JwtUtil;
import tools.jackson.databind.ObjectMapper;

@Service
public class MiniappServiceImpl implements MiniappService {
    private final RestClient restClient;
    private final String wxAppId;
    private final String wxAppSecret;
    private final MiniappUserRepository miniappUserRepository;
    private final JwtUtil jwtUtil;
    private final ObjectMapper objectMapper;

    public MiniappServiceImpl(RestClient.Builder restClientBuilder,
                              @Value("${app.miniapp.app-id}") String wxAppId,
                              @Value("${app.miniapp.secret}") String wxAppSecret,
                              MiniappUserRepository miniappUserRepository,
                              JwtUtil jwtUtil,
                              ObjectMapper objectMapper) {
        this.restClient = restClientBuilder.build();
        this.wxAppId = wxAppId;
        this.wxAppSecret = wxAppSecret;
        this.miniappUserRepository = miniappUserRepository;
        this.jwtUtil = jwtUtil;
        this.objectMapper = objectMapper;
    }

    @Override
    public MiniappLoginResult miniappLogin(String code) {
        if (code == null || code.isBlank()) {
            throw new BusinessException(400, "code不能为空");
        }

        String rawResponse = restClient.get()
                .uri(uriBuilder -> uriBuilder
                        .scheme("https")
                        .host("api.weixin.qq.com")
                        .path("/sns/jscode2session")
                        .queryParam("appid", wxAppId)
                        .queryParam("secret", wxAppSecret)
                        .queryParam("js_code", code)
                        .queryParam("grant_type", "authorization_code")
                        .build())
                .retrieve()
                .body(String.class);

        if (rawResponse == null) {
            throw new BusinessException(502, "微信登录服务未返回结果");
        }

        WechatSessionResponse response =
                objectMapper.readValue(rawResponse, WechatSessionResponse.class);
        if (response == null) {
            throw new BusinessException(502, "微信登录服务返回了无效结果");
        }
        if (response.getErrcode() != null && response.getErrcode() != 0) {
            throw new BusinessException(401, "微信登录失败: " + response.getErrmsg());
        }

        String openid = response.getOpenid();
        if (openid == null || openid.isBlank()) {
            throw new BusinessException(502, "微信登录响应缺少 openid");
        }

        WxUser wxUser = miniappUserRepository.findByOpenid(openid);
        if (wxUser == null) {
            wxUser = new WxUser();
            wxUser.setOpenid(openid);
            wxUser.setId(IdGenerator.generateId());
            miniappUserRepository.insertMiniappUser(wxUser);
        }
        String accessToken = jwtUtil.generateMiniappAccessToken(wxUser.getId(), wxUser.getOpenid());
        String refreshToken = jwtUtil.generateMiniappRefreshToken(wxUser.getId(), wxUser.getOpenid());
        return new MiniappLoginResult(accessToken, refreshToken);
    }
}
