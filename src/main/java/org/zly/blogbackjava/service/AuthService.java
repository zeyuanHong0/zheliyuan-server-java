package org.zly.blogbackjava.service;

import jakarta.validation.Valid;
import org.zly.blogbackjava.common.response.ApiResponse;
import org.zly.blogbackjava.dto.request.UserSigninDto;
import org.zly.blogbackjava.dto.request.UserSignupDto;

import java.util.Map;

public interface AuthService {
    void signin(@Valid UserSigninDto userSigninDto);

    void signup(@Valid UserSignupDto userSignupDto);

    void sendCode(@Valid String email);

    ApiResponse<Map<String, Boolean>> checkEmailBeforeRegister(@Valid String email);
}
