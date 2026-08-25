package org.zly.blogbackjava.service;

public interface EmailService {
    void sendCodeEmail(String email, String code);
}
