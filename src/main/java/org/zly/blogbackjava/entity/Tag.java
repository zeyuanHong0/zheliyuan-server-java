package org.zly.blogbackjava.entity;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Tag {

    private String id;

    private String name;

    private String icon;

    private String iconDark;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

    private Integer isDelete;
}
