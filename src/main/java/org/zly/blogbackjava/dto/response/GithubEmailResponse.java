package org.zly.blogbackjava.dto.response;

public record GithubEmailResponse(
        String email,
        boolean primary,
        boolean verified,
        String visibility
) {
}
