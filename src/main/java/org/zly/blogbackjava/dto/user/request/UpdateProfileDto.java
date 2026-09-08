package org.zly.blogbackjava.dto.user.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UpdateProfileDto {

    private String username;

    private String password;

    private String confirmPassword;
}
