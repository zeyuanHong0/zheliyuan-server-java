package org.zly.blogbackjava.service.impl;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.zly.blogbackjava.exception.BusinessException;
import org.zly.blogbackjava.service.EmailService;

@Service
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
        } catch (MessagingException e) {
            throw new BusinessException(500, "邮件内容创建失败");
        } catch (MailException e) {
            throw new BusinessException(500, "邮件发送失败，请稍后重试");
        }
    }
}