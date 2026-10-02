package org.zly.blogbackjava.dto.status.response;

public record StatusItem(
        Integer is_online,
        String status_text,
        String status_desc
) {
}
