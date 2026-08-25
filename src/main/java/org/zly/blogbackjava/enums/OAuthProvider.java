package org.zly.blogbackjava.enums;

import lombok.Getter;

@Getter
public enum OAuthProvider {
    GITHUB("github", "GitHub"),
    GOOGLE("google", "Google");

    private final String code;
    private final String name;

    OAuthProvider(String code, String name) {
        this.code = code;
        this.name = name;
    }
}
