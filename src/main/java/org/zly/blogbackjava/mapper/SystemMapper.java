package org.zly.blogbackjava.mapper;

import java.time.LocalDateTime;

public interface SystemMapper {
    LocalDateTime selectCurrentTime();
}
