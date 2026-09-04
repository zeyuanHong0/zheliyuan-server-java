package org.zly.blogbackjava.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CreateCategoryDto {

    @NotBlank(message = "分类名称不能为空")
    @Size(max = 6, message = "分类名称不能超过6个字符")
    private String name;
}
