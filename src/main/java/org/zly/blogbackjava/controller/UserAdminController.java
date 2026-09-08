package org.zly.blogbackjava.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.zly.blogbackjava.common.response.ApiResponse;
import org.zly.blogbackjava.dto.user.request.ChangeUserStatusDto;
import org.zly.blogbackjava.dto.user.request.UserListAdminDto;
import org.zly.blogbackjava.dto.user.response.UserListAdminResult;
import org.zly.blogbackjava.entity.User;
import org.zly.blogbackjava.service.UserService;

import java.util.List;

@RestController
@PreAuthorize("hasRole('ADMIN')")
@RequestMapping("/admin/user")
public class UserAdminController {
    private final UserService userService;

    public UserAdminController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/list")
    public ApiResponse<UserListAdminResult> getUserList(UserListAdminDto query) {
        UserListAdminResult result = userService.getUserList(query);
        return ApiResponse.success("获取列表成功", result);
    }

    @PostMapping("/changeUserStatus")
    public ApiResponse<Void> changeUserStatus(@RequestBody ChangeUserStatusDto changeUserStatusDto) {
        userService.changeUserStatus(changeUserStatusDto);
        return ApiResponse.success("修改用户状态成功", null);
    }
}
