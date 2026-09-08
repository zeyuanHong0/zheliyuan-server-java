package org.zly.blogbackjava.dto.auth.response;

public record GithubEmailResponse(
        String email,
        boolean primary,
        boolean verified,
        String visibility
) {
}
