package org.zly.blogbackjava.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.zly.blogbackjava.annotation.RequireLogin;
import org.zly.blogbackjava.common.response.ApiResponse;
import org.zly.blogbackjava.entity.User;
import org.zly.blogbackjava.service.StatsService;

import java.util.Map;

@RestController
@RequestMapping("/stats")
@RequireLogin
public class StatsDashboardController {

    private final StatsService statsService;

    public StatsDashboardController(StatsService statsService) {
        this.statsService = statsService;
    }

    @GetMapping("/dashboard")
    public ApiResponse<Map<String, Object>> getDashboardStats(@AuthenticationPrincipal User user) {
        Map<String, Object> stats = statsService.getDashboardStats(user);
        return ApiResponse.success("获取成功", stats);
    }
}
