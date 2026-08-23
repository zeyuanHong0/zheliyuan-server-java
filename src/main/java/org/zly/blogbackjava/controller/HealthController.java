package org.zly.blogbackjava.controller;

import org.zly.blogbackjava.common.response.ApiResponse;
import org.zly.blogbackjava.dto.response.HealthStatusResponse;
import org.zly.blogbackjava.service.SystemService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/health")
@RequiredArgsConstructor
public class HealthController {

    private final SystemService systemService;

    @Value("${spring.application.name}")
    private String applicationName;

    @GetMapping
    public ApiResponse<HealthStatusResponse> health() {
        HealthStatusResponse response = new HealthStatusResponse(applicationName, "UP", systemService.currentTime());
        return ApiResponse.success(response);
    }
}
