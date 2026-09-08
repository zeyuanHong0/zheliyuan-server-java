package org.zly.blogbackjava.dto.tag.response;

import java.time.LocalDateTime;

public record TagPageListItem(
        String id,
        String name,
        String icon,
        String icon_dark,
        LocalDateTime createTime,
        LocalDateTime updateTime
) {
}
