package org.zly.blogbackjava.dto.tag.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;

public record TagInfoResult(
        String id,
        String name,
        String icon,
        @JsonProperty("icon_dark") String iconDark,
        LocalDateTime createTime,
        LocalDateTime updateTime
) {
}
