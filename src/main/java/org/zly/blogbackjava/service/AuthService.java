package org.zly.blogbackjava.service;

import jakarta.validation.Valid;
import org.zly.blogbackjava.dto.request.UserSigninDto;
import org.zly.blogbackjava.dto.request.UserSignupDto;

public interface AuthService {
    void signin(@Valid UserSigninDto userSigninDto);

    void signup(@Valid UserSignupDto userSignupDto);

    void sendCode(@Valid String email);

    void checkEmailBeforeRegister(@Valid String email);
}
