package org.zly.blogbackjava.service;

import org.zly.blogbackjava.dto.request.UserSigninDto;
import org.zly.blogbackjava.dto.request.UserSignupDto;
import org.zly.blogbackjava.dto.response.EmailAvailabilityResult;

public interface AuthService {
    String signin(UserSigninDto userSigninDto);

    String signup(UserSignupDto userSignupDto);

    void sendCode(String email);

    EmailAvailabilityResult checkEmailBeforeRegister(String email);
}
