package org.zly.blogbackjava.dto.blog.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ChangeBlogStatusDto {

    @NotNull(message = "发布状态不能为空")
    @Min(value = 0, message = "发布状态只能为 0 或 1")
    @Max(value = 1, message = "发布状态只能为 0 或 1")
    private Integer published;
}
