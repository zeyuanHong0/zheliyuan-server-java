package org.zly.blogbackjava.util;

import java.util.UUID;

/**
 * ID 生成工具类
 */
public class IdGenerator {

    /**
     * 生成32位UUID
     */
    public static String generateId() {
        return UUID.randomUUID().toString();
    }
}

