package org.zly.blogbackjava.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.zly.blogbackjava.common.response.ApiResponse;
import org.zly.blogbackjava.dto.status.response.StatusItem;
import org.zly.blogbackjava.service.StatusService;

@RestController
@RequestMapping("/status")
public class StatusController {

    private final StatusService statusService;

    public StatusController(StatusService statusService) {
        this.statusService = statusService;
    }

    @GetMapping("/getStatus")
    public ApiResponse<StatusItem> getStatus() {
        StatusItem statusItem = statusService.getStatus();
        return ApiResponse.success("获取成功", statusItem);
    }
}
