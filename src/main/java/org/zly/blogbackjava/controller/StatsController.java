package org.zly.blogbackjava.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.zly.blogbackjava.common.response.ApiResponse;
import org.zly.blogbackjava.service.StatsService;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/stats")
public class StatsController {

    private final StatsService statsService;

    public StatsController(StatsService statsService) {
        this.statsService = statsService;
    }

    @GetMapping("/blog")
    public ApiResponse<Map<String, Integer>> getBlogStats() {
        List<Integer> result = statsService.getBlogStats();
        return ApiResponse.success(Map.of(
                "blogCount", result.get(0),
                "tagCount", result.get(1),
                "categoryCount", result.get(2)
        ));
    }
}
