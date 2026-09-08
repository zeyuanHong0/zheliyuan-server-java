package org.zly.blogbackjava.dto.category.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class UpdateCategoryDto extends CreateCategoryDto {
    @NotBlank(message = "分类ID不能为空")
    private String id;
}
