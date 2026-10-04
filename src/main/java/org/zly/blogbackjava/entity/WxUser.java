package org.zly.blogbackjava.entity;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class WxUser {
    private String id;

    private String openid;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

    private Integer isDelete;
}
