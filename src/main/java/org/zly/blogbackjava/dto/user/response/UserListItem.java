package org.zly.blogbackjava.dto.user.response;

import java.time.LocalDateTime;

public record UserListItem(
        String id,
        String username,
        String email,
        LocalDateTime createTime,
        LocalDateTime updateTime,
        Integer isDelete
) {
}
