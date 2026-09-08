package org.zly.blogbackjava.dto.tag.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.EqualsAndHashCode;


@Data
@EqualsAndHashCode(callSuper = true)
public class UpdateTagDto extends CreateTagDto {
    @NotBlank(message = "标签ID不能为空")
    private String id;
}
