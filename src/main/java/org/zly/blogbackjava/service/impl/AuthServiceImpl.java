package org.zly.blogbackjava.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.zly.blogbackjava.common.response.ApiResponse;
import org.zly.blogbackjava.dto.request.UserSigninDto;
import org.zly.blogbackjava.dto.request.UserSignupDto;
import org.zly.blogbackjava.entity.EmailCodes;
import org.zly.blogbackjava.entity.User;
import org.zly.blogbackjava.exception.BusinessException;
import org.zly.blogbackjava.repository.EmailCodesRepository;
import org.zly.blogbackjava.service.AuthService;
import org.zly.blogbackjava.service.EmailService;
import org.zly.blogbackjava.service.UserService;
import org.zly.blogbackjava.util.IdGenerator;

import java.util.Map;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.concurrent.ThreadLocalRandom;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final EmailCodesRepository emailCodesRepository;
    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService;
    private final UserService userService;

    // 生成6位随机验证码
    private String generateCode() {
        int code = ThreadLocalRandom.current().nextInt(0, 1000000);
        return String.format("%06d", code);
    }

    // 校验验证码
    private Boolean verifyCode(String email, String code) {
        EmailCodes latest = emailCodesRepository.selectLatestValidCodeByEmail(email);
        if (latest == null) {
            throw new BusinessException(400, "验证码不存在或已过期");
        }
        // 检查验证码是否匹配
        if (!passwordEncoder.matches(code, latest.getCode())) {
            throw new BusinessException(400, "验证码不正确");
        }
        // 标记验证码为已使用
        emailCodesRepository.markCodeAsUsed(latest.getId());
        return true;
    }

    @Override
    public ApiResponse<Map<String, Boolean>> checkEmailBeforeRegister(String email) {
        // 校验邮箱格式
        String reg = "^[^\\s@]+@[^\\s@]+\\.[^\\s@]+$";
        if (!email.matches(reg)) {
            throw new BusinessException(400, "邮箱格式不正确");
        }
        // 检查邮箱是否已存在
        User user = userService.findUserByEmail(email);
        if (user != null) {
            // 如果用户没有密码，说明是第三方登录的
            if (user.getPassword() == null) {
                String provider = null;
                if (user.getUserOauths() != null && !user.getUserOauths().isEmpty()) {
                    provider = user.getUserOauths().get(0).getProvider();
                }
                String message = "该邮箱已通过" + (provider != null ? provider : "第三方") + "登录，继续注册将为账号设置密码";
                return ApiResponse.success(message, Map.of("needSetPassword", true));
            }
            throw new BusinessException(409, "邮箱已存在");
        }
        return ApiResponse.success("邮箱可用", Map.of("needSetPassword", false));
    }

    @Override
    public void signin(UserSigninDto userSigninDto) {
    }

    @Override
    @Transactional
    public ApiResponse<Void> signup(UserSignupDto userSignupDto) {
        String userName = userSignupDto.getUsername();
        String password = userSignupDto.getPassword();
        String email = userSignupDto.getEmail();
        String emailCode = userSignupDto.getEmailCode();
        // 检查邮箱是否存在
        User existingEmailUser = userService.findUserByEmail(email);
        if (existingEmailUser != null && existingEmailUser.getPassword() != null) {
            throw new BusinessException(409, "邮箱已存在");
        }

        // 检查用户名是否已存在（合并场景下允许使用 OAuth 用户自己原有的用户名）
        User existingUser = userService.findUserByUserName(userName);
        if (existingUser != null && (existingEmailUser == null || !Objects.equals(existingUser.getId(), existingEmailUser.getId()))) {
            throw new BusinessException(409, "用户名已存在");
        }

        // 校验邮箱验证码
        Boolean isVerify = this.verifyCode(email, emailCode);
        if (isVerify) {
            // 加密密码
            String hashedPassword = passwordEncoder.encode(password);
            // 合并 OAuth 用户:同步更新用户名和密码
            if (existingEmailUser != null && existingEmailUser.getPassword() == null) {
                // 更新用户信息
                existingEmailUser.setPassword(hashedPassword);
                existingEmailUser.setUsername(userName);
                userService.updateUser(existingEmailUser);
            } else {
                // 创建新用户
                User newUser = new User();
                newUser.setId(IdGenerator.generateId());
                newUser.setUsername(userName);
                newUser.setPassword(hashedPassword);
                newUser.setEmail(email);
                userService.saveUser(newUser);
            }
        }
        return ApiResponse.success("注册成功", null);
    }

    @Override
    public void sendCode(String email) {
        EmailCodes latest = emailCodesRepository.selectLatestCodeByEmail(email);
        if (latest != null && latest.getCreateTime() != null) {
            LocalDateTime now = LocalDateTime.now();
            if (now.minusSeconds(60).isBefore(latest.getCreateTime())) {
                throw new BusinessException(429, "发送过于频繁，请稍后再试");
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
