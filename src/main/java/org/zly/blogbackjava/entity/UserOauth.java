package org.zly.blogbackjava.entity;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UserOauth {
    private String id;

    private String provider;

    private String providerId;

    private String userId;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;
}
