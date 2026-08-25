package org.zly.blogbackjava.dto.request;


import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UserSigninDto {

    @NotBlank(message = "用户名不能为空")
    private String username;

    @NotBlank(message = "密码不能为空")
    private String password;
}
