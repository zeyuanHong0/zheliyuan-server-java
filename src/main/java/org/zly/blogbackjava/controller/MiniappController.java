package org.zly.blogbackjava.controller;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.zly.blogbackjava.common.response.ApiResponse;
import org.zly.blogbackjava.dto.miniapp.request.MiniappLoginDto;
import org.zly.blogbackjava.dto.miniapp.response.MiniappLoginResult;
import org.zly.blogbackjava.service.MiniappService;

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
}
