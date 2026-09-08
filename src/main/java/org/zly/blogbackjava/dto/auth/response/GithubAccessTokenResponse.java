package org.zly.blogbackjava.dto.auth.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public record GithubAccessTokenResponse(
        @JsonProperty("access_token") String accessToken,
        String scope,
        @JsonProperty("token_type") String tokenType,
        String error,
        @JsonProperty("error_description") String errorDescription
) {
}
