package org.zly.blogbackjava.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.zly.blogbackjava.annotation.RequireLogin;
import org.zly.blogbackjava.common.response.ApiResponse;
import org.zly.blogbackjava.dto.request.UpdateProfileDto;
import org.zly.blogbackjava.dto.response.UserProfileDto;
import org.zly.blogbackjava.entity.User;
import org.zly.blogbackjava.service.UserService;
import org.zly.blogbackjava.util.CookieUtil;

import java.util.Map;

@RestController
@RequestMapping("/user")
@RequireLogin
public class UserController {
    private final UserService userService;
    private final CookieUtil cookieUtil;

    public UserController(UserService userService, CookieUtil cookieUtil) {
        this.userService = userService;
        this.cookieUtil = cookieUtil;
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
     * @param response          HTTP 响应对象
     */
    @PutMapping("/update/profile")
    public ApiResponse<Void> updateProfile(@AuthenticationPrincipal User user, @RequestBody UpdateProfileDto updateProfileDto, HttpServletResponse response) {
        userService.updateProfile(user.getId(), updateProfileDto);
        cookieUtil.clearTokenCookie(response);
        return ApiResponse.success("更新成功", null);
    }
}
