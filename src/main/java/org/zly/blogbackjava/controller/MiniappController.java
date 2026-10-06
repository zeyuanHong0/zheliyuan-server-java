package org.zly.blogbackjava.controller;

import jakarta.validation.Valid;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.zly.blogbackjava.common.response.ApiResponse;
import org.zly.blogbackjava.dto.miniapp.request.MiniappLoginDto;
import org.zly.blogbackjava.dto.miniapp.response.MiniappLoginResult;
import org.zly.blogbackjava.entity.WxUser;
import org.zly.blogbackjava.service.MiniappService;

import java.util.Map;

@RestController
@RequestMapping("/miniapp")
public class MiniappController {

    private final MiniappService miniappService;

    public MiniappController(MiniappService miniappService) {
        this.miniappService = miniappService;
    }

    @PostMapping("/login")
    public ApiResponse<MiniappLoginResult> miniappLogin(@RequestBody @Valid MiniappLoginDto loginDto) {
        MiniappLoginResult result = miniappService.miniappLogin(loginDto.getCode());
        return ApiResponse.success("登录成功", result);
    }

    @PostMapping("/refresh")
    public ApiResponse<Map<String, String>> refreshToken(@AuthenticationPrincipal WxUser wxUser) {
        String accessToken = miniappService.refreshAccessToken(wxUser);
        return ApiResponse.success("刷新成功", Map.of("access_token", accessToken));
    }
}
