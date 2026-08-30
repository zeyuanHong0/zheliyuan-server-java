package org.zly.blogbackjava.controller;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;
import org.zly.blogbackjava.common.response.ApiResponse;
import org.zly.blogbackjava.dto.request.EmailRequestDto;
import org.zly.blogbackjava.dto.request.UserSigninDto;
import org.zly.blogbackjava.dto.request.UserSignupDto;
import org.zly.blogbackjava.dto.response.EmailAvailabilityResult;
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
    public void checkEmailBeforeForgotPassword(@RequestBody @Valid String email) {

    }

    @PostMapping("/forgotPassword/verifyCode")
    public void verifyCodeBeforeForgotPassword(@RequestBody @Valid String email, @RequestBody @Valid String code) {

    }

    @PostMapping("/forgotPassword/resetPassword")
    public void resetPassword() {

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
