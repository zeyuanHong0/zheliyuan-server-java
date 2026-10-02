package org.zly.blogbackjava.entity;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Status {
    private String id;

    private Integer isOnline;

    private String statusText;

    private String statusDesc;

    private LocalDateTime updateTime;
}
