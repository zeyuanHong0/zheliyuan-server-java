package org.zly.blogbackjava.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.zly.blogbackjava.annotation.RequireLogin;
import org.zly.blogbackjava.common.response.ApiResponse;
import org.zly.blogbackjava.dto.upload.response.UploadFileResult;
import org.zly.blogbackjava.entity.User;
import org.zly.blogbackjava.service.UploadService;

@RestController
@RequestMapping("/upload")
@RequireLogin
public class UploadController {

    private final UploadService uploadService;

    public UploadController(UploadService uploadService) {
        this.uploadService = uploadService;
    }

    @PostMapping("/fileToCos")
    public ApiResponse<UploadFileResult> uploadFileToCos(@RequestParam("file") MultipartFile file,
                                                          @AuthenticationPrincipal User user) {
        UploadFileResult result = uploadService.uploadFileToCos(file, user.getId());
        return ApiResponse.success("上传成功", result);
    }
}
