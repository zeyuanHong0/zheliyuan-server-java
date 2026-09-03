package org.zly.blogbackjava.entity;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Category {

    private String id;

    private String name;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

    private Integer isDelete;
}
