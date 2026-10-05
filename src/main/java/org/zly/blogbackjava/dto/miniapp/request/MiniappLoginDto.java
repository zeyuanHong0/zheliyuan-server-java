package org.zly.blogbackjava.dto.miniapp.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class MiniappLoginDto {

    @NotBlank(message = "code不能为空")
    private String code;
}
