package org.zly.blogbackjava.dto.user.response;

public record UserProfileDto(
        String id,
        String username,
        String email,
        String accountType
) {
}
