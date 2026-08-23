package org.zly.blogbackjava.entity;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public abstract class BaseEntity {
    private Long id;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
