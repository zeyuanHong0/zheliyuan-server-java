package org.zly.blogbackjava.service.impl;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import org.springframework.web.util.UriComponentsBuilder;
import org.zly.blogbackjava.common.response.ApiResponse;
import org.zly.blogbackjava.dto.response.GithubAccessTokenResponse;
import org.zly.blogbackjava.dto.response.GithubEmailResponse;
import org.zly.blogbackjava.dto.response.GithubUserResponse;
import org.zly.blogbackjava.enums.OAuthProvider;
import org.zly.blogbackjava.exception.BusinessException;
import org.zly.blogbackjava.repository.UserRepository;
import org.zly.blogbackjava.service.GithubService;
import org.zly.blogbackjava.service.UserService;
import org.zly.blogbackjava.util.JwtUtil;

import java.security.SecureRandom;
import java.util.Map;

@Service
public class GithubServiceImpl implements GithubService {

    private final String clientId;

    private final String redirectUri;

    private final String clientSecret;

    private static final SecureRandom SECURE_RANDOM = new SecureRandom();

    private final RestClient restClient;

    private final UserRepository userRepository;

    private final UserService userService;

    private final JwtUtil jwtUtil;

    public GithubServiceImpl(
            @Value("${app.github.client-id}") String clientId,
            @Value("${app.github.callback-url}") String redirectUri,
            @Value("${app.github.client-secret}") String clientSecret,
            RestClient.Builder restClientBuilder,
            UserRepository userRepository,
            UserService userService,
            JwtUtil jwtUtil
    ) {
        this.clientId = clientId;
        this.redirectUri = redirectUri;
        this.clientSecret = clientSecret;
        this.restClient = restClientBuilder.build();
        this.userRepository = userRepository;
        this.userService = userService;
        this.jwtUtil = jwtUtil;
    }

    /**
     * 获取 GitHub access_token
     *
     * @param code GitHub 授权码
     * @return access_token
     */
    private String getGithubAccessToken(String code) {
        GithubAccessTokenResponse response = restClient.post()
                .uri("https://github.com/login/oauth/access_token")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .header("User-Agent", "zlyhub-app")
                .body(Map.of(
                        "client_id", clientId,
                        "client_secret", clientSecret,
                        "code", code,
                        "redirect_uri", redirectUri
                ))
                .retrieve()
                .body(GithubAccessTokenResponse.class);

        if (response == null || response.accessToken() == null) {
            String message = response != null && response.errorDescription() != null
                    ? response.errorDescription()
                    : "GitHub 授权失败";
            throw new BusinessException(401, message);
        }

        return response.accessToken();
    }

    /**
     * 获取 GitHub 用户信息
     *
     * @param accessToken GitHub access_token
     * @return GitHub 用户信息
     */
    private GithubUserResponse getGithubUserInfo(String accessToken) {
        GithubUserResponse response = restClient.get()
                .uri("https://api.github.com/user")
                .header("Authorization", "Bearer " + accessToken)
                .header("User-Agent", "zlyhub-app")
                .retrieve()
                .body(GithubUserResponse.class);
        if (response == null || response.id() == null) {
            throw new BusinessException(401, "获取 GitHub 用户信息失败");
        }
        return response;
    }

    /**
     * 获取 GitHub 用户邮箱
     *
     * @param accessToken GitHub access_token
     * @return GitHub 用户邮箱
     */
    private GithubEmailResponse getGithubUserEmail(String accessToken) {
        GithubEmailResponse[] responses = restClient.get()
                .uri("https://api.github.com/user/emails")
                .header("Authorization", "Bearer " + accessToken)
                .header("User-Agent", "zlyhub-app")
                .retrieve()
                .body(GithubEmailResponse[].class);
        if (responses == null || responses.length == 0) {
            throw new BusinessException(401, "获取 GitHub 用户邮箱失败");
        }
        for (GithubEmailResponse emailResponse : responses) {
            if (emailResponse.primary() && emailResponse.verified()) {
                return emailResponse;
            }
        }
        throw new BusinessException(401, "未找到已验证的主邮箱");
    }

    @Override
    public ApiResponse<Map<String, String>> getGithubAuthorizeUrl() {
        String scope = "read:user user:email";
        String state = Long.toString(SECURE_RANDOM.nextLong(), 36);
        String url = UriComponentsBuilder
                .fromUriString("https://github.com/login/oauth/authorize")
                .queryParam("client_id", clientId)
                .queryParam("redirect_uri", redirectUri)
                .queryParam("scope", scope)
                .queryParam("state", state)
                .build()
                .encode()
                .toUriString();
        return ApiResponse.success("获取成功", Map.of("url", url, "state", state));
    }

    @Override
    public String githubLogin(String code) {
        // 获取 access_token
        String accessToken = getGithubAccessToken(code);
        // 获取用户信息
        GithubUserResponse githubUser = getGithubUserInfo(accessToken);
        Long githubId = githubUser.id();
        String name = githubUser.name();
        String email = githubUser.email();
        if (email == null) {
            GithubEmailResponse githubEmail = getGithubUserEmail(accessToken);
            email = githubEmail.email();
        }

        // 查找或创建用户
        var user = userRepository.findByProviderAndProviderId(OAuthProvider.GITHUB.getCode(), githubId.toString());
        if (user == null) {
            // 用户不存在就创建用户
            user = userService.createOauthUser(name, email, OAuthProvider.GITHUB, githubId);
        }
        return jwtUtil.generateToken(user.getId(), user.getUsername());
    }
}
