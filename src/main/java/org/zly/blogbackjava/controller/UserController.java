package org.zly.blogbackjava.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.zly.blogbackjava.annotation.RequireLogin;
import org.zly.blogbackjava.common.response.ApiResponse;
import org.zly.blogbackjava.dto.request.UpdateProfileDto;
import org.zly.blogbackjava.dto.response.UserProfileDto;
import org.zly.blogbackjava.entity.User;
import org.zly.blogbackjava.service.UserService;

import java.util.Map;

@RestController
@RequestMapping("/user")
@RequireLogin
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    /**
     * 获取当前登录用户的个人信息
     *
     * @param user 当前登录的用户对象
     */
    @GetMapping("/profile")
    public ApiResponse<Map<String, UserProfileDto>> getProfile(@AuthenticationPrincipal User user) {
        return ApiResponse.success(Map.of("userInfo", userService.getProfile(user.getId())));
    }

    /**
     * 检查当前登录用户是否为管理员
     *
     * @param user 当前登录的用户对象
     */
    @GetMapping("/isAdmin")
    public ApiResponse<Map<String, Boolean>> isAdmin(@AuthenticationPrincipal User user) {
        return ApiResponse.success(Map.of("isAdmin", userService.isAdmin(user.getId())));
    }

    /**
     * 更新当前登录用户的个人信息
     *
     * @param user             当前登录的用户对象
     * @param updateProfileDto 包含更新信息的 DTO 对象
     * @param request          HTTP 请求对象
     */
    @PutMapping("/update/Profile")
    public void updateProfile(@AuthenticationPrincipal User user, UpdateProfileDto updateProfileDto, HttpServletRequest request) {
    }
}
