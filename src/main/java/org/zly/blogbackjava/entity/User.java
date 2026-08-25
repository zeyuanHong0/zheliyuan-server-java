package org.zly.blogbackjava.entity;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class User {

    private String id;

    private String username;

    private String password;

    private String accountType;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

    private Boolean deleted;

}
