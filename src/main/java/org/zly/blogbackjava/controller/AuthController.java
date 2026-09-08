package org.zly.blogbackjava.controller;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;
import org.zly.blogbackjava.common.response.ApiResponse;
import org.zly.blogbackjava.dto.auth.request.*;
import org.zly.blogbackjava.dto.auth.response.EmailAvailabilityResult;
import org.zly.blogbackjava.service.AuthService;
import org.zly.blogbackjava.util.CookieUtil;

import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;
    private final CookieUtil cookieUtil;
    private final String githubRedirectPage;

    public AuthController(
            AuthService authService,
            CookieUtil cookieUtil,
            @Value("${app.github.redirect-page}") String githubRedirectPage
    ) {
        this.authService = authService;
        this.cookieUtil = cookieUtil;
        this.githubRedirectPage = githubRedirectPage;
    }

    @PostMapping("/signin")
    public ApiResponse<Void> login(@RequestBody @Valid UserSigninDto userSigninDto, HttpServletResponse response) {
        String token = authService.signin(userSigninDto);
        cookieUtil.setTokenCookie(response, token);
        return ApiResponse.success("登录成功", null);
    }

    @PostMapping("/signup")
    public ApiResponse<Void> register(@RequestBody @Valid UserSignupDto userSignupDto, HttpServletResponse response) {
        String token = authService.signup(userSignupDto);
        cookieUtil.setTokenCookie(response, token);
        return ApiResponse.success("注册成功", null);
    }

    @PostMapping("/signout")
    public ApiResponse<Void> logout(HttpServletResponse response) {
        cookieUtil.clearTokenCookie(response);
        return ApiResponse.success("退出登录成功", null);
    }

    @PostMapping("/checkEmail")
    public ApiResponse<Map<String, Boolean>> checkEmailBeforeRegister(@RequestBody @Valid EmailRequestDto emailRequestDto) {
        EmailAvailabilityResult result = authService.checkEmailBeforeRegister(emailRequestDto.getEmail());
        return ApiResponse.success(result.message(), Map.of("needSetPassword", result.needSetPassword()));
    }

    @PostMapping("/forgotPassword/checkEmail")
    public ApiResponse<Map<String, Boolean>> checkEmailBeforeForgotPassword(@RequestBody @Valid EmailRequestDto emailRequestDto) {
        boolean exists = authService.forgetPasswordCheckEmail(emailRequestDto.getEmail());
        return ApiResponse.success(exists ? "邮箱可用" : "邮箱不存在", Map.of("checkEmail", exists));
    }

    @PostMapping("/forgotPassword/verifyCode")
    public ApiResponse<Map<String, String>> verifyCodeBeforeForgotPassword(
            @RequestBody @Valid VerifyCodeRequestDto verifyCodeRequestDto) {
        authService.verifyCode(verifyCodeRequestDto.getEmail(), verifyCodeRequestDto.getCode());
        String resetToken = authService.generateResetToken(verifyCodeRequestDto.getEmail());
        return ApiResponse.success("验证码校验成功", Map.of("resetToken", resetToken));
    }

    @PostMapping("/forgotPassword/resetPassword")
    public ApiResponse<Void> resetPassword(@RequestBody @Valid RestPasswordDto resetPasswordDto) {
        authService.resetPassword(resetPasswordDto.getResetToken(), resetPasswordDto.getNewPassword());
        return ApiResponse.success("密码重置成功", null);
    }

    @PostMapping("/sendCode")
    public ApiResponse<Void> sendCode(@RequestBody @Valid EmailRequestDto emailRequestDto) {
        authService.sendCode(emailRequestDto.getEmail());
        return ApiResponse.success("验证码发送成功", null);
    }

    @GetMapping("/github/url")
    public ApiResponse<Map<String, String>> getGithubAuthorizeUrl() {
        return authService.getGithubAuthorizeUrl();
    }

    @GetMapping("/github/callback")
    public void githubCallback(@RequestParam("code") String code, HttpServletResponse response) {
        String token = authService.githubLogin(code);
        cookieUtil.setTokenCookie(response, token);
        response.setStatus(HttpServletResponse.SC_FOUND);
        response.setHeader(HttpHeaders.LOCATION, githubRedirectPage);
    }

}
