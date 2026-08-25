package org.zly.blogbackjava.service.impl;

import jakarta.validation.Valid;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.zly.blogbackjava.dto.request.UserSigninDto;
import org.zly.blogbackjava.dto.request.UserSignupDto;
import org.zly.blogbackjava.entity.EmailCodes;
import org.zly.blogbackjava.repository.EmailCodesRepository;
import org.zly.blogbackjava.service.AuthService;
import org.zly.blogbackjava.service.EmailService;
import org.zly.blogbackjava.util.IdGenerator;

import java.time.LocalDateTime;
import java.util.concurrent.ThreadLocalRandom;

@Service
public class AuthServiceImpl implements AuthService {
    private final EmailCodesRepository emailCodesRepository;
    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService;

    public AuthServiceImpl(EmailCodesRepository emailCodesRepository, PasswordEncoder passwordEncoder, EmailService emailService) {
        this.emailCodesRepository = emailCodesRepository;
        this.passwordEncoder = passwordEncoder;
        this.emailService = emailService;
    }

    // 生成6位随机验证码
    private String generateCode() {
        int code = ThreadLocalRandom.current().nextInt(0, 1000000);
        return String.format("%06d", code);
    }

    @Override
    public void checkEmailBeforeRegister(String email) {
        // 校验邮箱格式
        String reg = "^[^\\s@]+@[^\\s@]+\\.[^\\s@]+$";
        if (!email.matches(reg)) {
            throw new RuntimeException("邮箱格式不正确");
        }
    }

    @Override
    public void signin(UserSigninDto userSigninDto) {
    }

    @Override
    public void signup(UserSignupDto userSignupDto) {
    }

    @Override
    public void sendCode(String email) {
        EmailCodes latest = emailCodesRepository.selectLatestCodeByEmail(email);
        if (latest != null && latest.getCreateTime() != null) {
            LocalDateTime now = LocalDateTime.now();
            if (now.minusSeconds(60).isBefore(latest.getCreateTime())) {
                throw new RuntimeException("发送过于频繁，请稍后再试");
            }
        }
        // 生成验证码
        String code = generateCode();
        String hashedCode = passwordEncoder.encode(code);
        LocalDateTime expiredTime = LocalDateTime.now().plusMinutes(5); // 验证码有效期为5分钟
        emailCodesRepository.insertEmailCode(IdGenerator.generateId(), email, hashedCode, expiredTime);

        // 发送邮件
        emailService.sendCodeEmail(email, code);
    }
}
