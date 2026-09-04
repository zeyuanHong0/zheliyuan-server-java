package org.zly.blogbackjava.dto.response;

import java.time.LocalDateTime;

public record CategoryPageListItem(
        String id,
        String name,
        LocalDateTime createTime,
        LocalDateTime updateTime
) {
}
