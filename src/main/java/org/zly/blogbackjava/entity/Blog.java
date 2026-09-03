package org.zly.blogbackjava.entity;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Blog {

    private String id;

    private String title;

    private String description;

    private String content;

    private Integer published;

    private String createUser;

    private String aiSummary;

    private String categoryId;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

    private Integer isDelete;
}
