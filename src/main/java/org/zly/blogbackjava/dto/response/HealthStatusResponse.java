package org.zly.blogbackjava.dto.response;

import java.time.LocalDateTime;

public record HealthStatusResponse(String application, String status, LocalDateTime serverTime) {
}
