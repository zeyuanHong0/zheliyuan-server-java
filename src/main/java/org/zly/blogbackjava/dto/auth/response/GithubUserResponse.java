package org.zly.blogbackjava.dto.auth.response;

public record GithubUserResponse(
        Long id,
        String login,
        String node_id,
        String avatar_url,
        String name,
        String email
) {
}
