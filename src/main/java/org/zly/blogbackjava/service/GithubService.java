package org.zly.blogbackjava.service;

import org.zly.blogbackjava.common.response.ApiResponse;

import java.util.Map;

public interface GithubService {
    ApiResponse<Map<String, String>> getGithubAuthorizeUrl();

    String githubLogin(String code);
}
