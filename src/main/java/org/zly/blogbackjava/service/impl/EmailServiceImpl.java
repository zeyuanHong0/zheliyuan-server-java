package org.zly.blogbackjava.service.impl;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.zly.blogbackjava.exception.BusinessException;
import org.zly.blogbackjava.service.EmailService;

@Service
@Slf4j
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender mailSender;
    private final String from;

    public EmailServiceImpl(JavaMailSender mailSender, @Value("${app.email.from}") String from) {
        this.mailSender = mailSender;
        this.from = from;
    }

    @Override
    public void sendCodeEmail(String email, String code) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            helper.setFrom(from);
            helper.setTo(email);
            helper.setSubject("您的博客验证码");

            String html = """
                    <p>你的验证码是：</p>
                    <h2 style="font-size: 30px">%s</h2>
                    <p>5 分钟内有效，请勿泄露。</p>
                    """.formatted(code);

            helper.setText(html, true);
            mailSender.send(message);
            log.info("验证码邮件已发送，收件邮箱={}", maskEmail(email));
        } catch (MessagingException e) {
            log.error("创建验证码邮件失败，收件邮箱={}", maskEmail(email), e);
            throw new BusinessException(500, "邮件内容创建失败");
        } catch (MailException e) {
            log.error("发送验证码邮件失败，收件邮箱={}", maskEmail(email), e);
            throw new BusinessException(500, "邮件发送失败，请稍后重试");
        }
    }

    private String maskEmail(String email) {
        if (email == null || email.isBlank()) {
            return "<空>";
        }
        int atIndex = email.indexOf('@');
        if (atIndex <= 0) {
            return "***";
        }
        return email.charAt(0) + "***" + email.substring(atIndex);
    }
}