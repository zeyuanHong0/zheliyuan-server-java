package org.zly.blogbackjava.enums;


import lombok.Getter;

@Getter
public enum Role {
    ADMIN("admin", "管理员"),
    TOURIST("test", "游客");

    private final String code;
    private final String name;

    Role(String code, String name) {
        this.code = code;
        this.name = name;
    }
}
