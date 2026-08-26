package org.zly.blogbackjava.controller;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import org.zly.blogbackjava.common.response.ApiResponse;
import org.zly.blogbackjava.dto.request.EmailRequestDto;
import org.zly.blogbackjava.dto.request.UserSigninDto;
import org.zly.blogbackjava.dto.request.UserSignupDto;
import org.zly.blogbackjava.service.AuthService;

import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/signin")
    public void login(@RequestBody @Valid UserSigninDto userSigninDto) {
        authService.signin(userSigninDto);
    }

    @PostMapping("/signup")
    public void register(@RequestBody @Valid UserSignupDto userSignupDto) {
        authService.signup(userSignupDto);
    }

    @PostMapping("/signout")
    public void logout() {

    }

    @PostMapping("/checkEmail")
    public ApiResponse<Map<String, Boolean>> checkEmailBeforeRegister(@RequestBody @Valid EmailRequestDto emailRequestDto) {
        return authService.checkEmailBeforeRegister(emailRequestDto.getEmail());
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
    public void sendCode(@RequestBody @Valid EmailRequestDto emailRequestDto) {
        authService.sendCode(emailRequestDto.getEmail());
    }

    @GetMapping("/github/url")
    public void getGithubAuthorizeUrl() {

    }

    @GetMapping("/github/callback")
    public void githubCallback() {

    }

}
