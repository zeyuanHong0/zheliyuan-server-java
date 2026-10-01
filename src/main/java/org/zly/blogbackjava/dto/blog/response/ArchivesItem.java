package org.zly.blogbackjava.dto.blog.response;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ArchivesItem {
    private String id;

    private String title;

    private LocalDateTime createTime;
}
