package org.zly.blogbackjava.entity;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UploadFile {

    private String id;

    @NotBlank(message = "原始文件名不能为空")
    @Size(max = 255, message = "原始文件名不能超过255个字符")
    private String originalName;

    @NotBlank(message = "存储文件名不能为空")
    @Size(max = 255, message = "存储文件名不能超过255个字符")
    private String filename;

    @NotBlank(message = "文件 MIME 类型不能为空")
    @Size(max = 255, message = "文件 MIME 类型不能超过255个字符")
    private String mimeType;

    @NotNull(message = "文件大小不能为空")
    @PositiveOrZero(message = "文件大小不能小于0")
    @Max(value = 50 * 1024 * 1024, message = "文件大小不能超过50MB")
    private Long size;

    @NotBlank(message = "存储类型不能为空")
    @Pattern(regexp = "local|cos", message = "存储类型只能为 local 或 cos")
    private String storageType;

    @Size(max = 2048, message = "文件地址不能超过2048个字符")
    private String fileUrl;

    private LocalDateTime createTime;

    @Size(max = 255, message = "上传者 ID 不能超过255个字符")
    private String uploadedBy;
}
