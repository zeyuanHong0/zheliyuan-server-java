package org.zly.blogbackjava.dto.category.response;

import java.time.LocalDateTime;

public record CategoryInfoResult(
        String id,
        String name,
        LocalDateTime createTime,
        LocalDateTime updateTime
) {
}
