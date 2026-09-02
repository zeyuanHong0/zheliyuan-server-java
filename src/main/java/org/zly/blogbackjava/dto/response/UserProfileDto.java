package org.zly.blogbackjava.dto.response;

public record UserProfileDto(
        String id,
        String username,
        String email,
        String accountType
) {
}
