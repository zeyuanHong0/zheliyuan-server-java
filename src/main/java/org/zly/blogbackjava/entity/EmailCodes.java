package org.zly.blogbackjava.entity;


import lombok.Data;

import java.time.LocalDateTime;

@Data
public class EmailCodes {
    private String id;

    private String email;

    private String code;

    private Boolean used;

    private LocalDateTime createdTime;

    private LocalDateTime expiresTime;
}
