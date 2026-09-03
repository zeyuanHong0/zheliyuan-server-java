package org.zly.blogbackjava.entity;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class User {

    private String id;

    private String username;

    private String password;

    private String email;

    private String accountType;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

    private Integer isDelete;

    private List<UserOauth> userOauths;

}
