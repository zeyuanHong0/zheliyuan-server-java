package org.zly.blogbackjava.service;

import org.zly.blogbackjava.common.response.ApiResponse;
import org.zly.blogbackjava.dto.auth.request.UserSigninDto;
import org.zly.blogbackjava.dto.auth.request.UserSignupDto;
import org.zly.blogbackjava.dto.auth.response.EmailAvailabilityResult;
import org.zly.blogbackjava.entity.User;

import java.util.Map;

public interface AuthService {
    String signin(UserSigninDto userSigninDto);

    String signup(UserSignupDto userSignupDto);

    void sendCode(String email);

    boolean verifyCode(String email, String code);

    String generateResetToken(String email);

    EmailAvailabilityResult checkEmailBeforeRegister(String email);

    boolean forgetPasswordCheckEmail(String email);

    void resetPassword(String resetToken, String newPassword);

    ApiResponse<Map<String, String>> getGithubAuthorizeUrl();

    String githubLogin(String code);
}
