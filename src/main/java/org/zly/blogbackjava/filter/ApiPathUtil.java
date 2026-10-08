package org.zly.blogbackjava.filter;

public final class ApiPathUtil {
    private static final String API_PREFIX = "/api";

    private ApiPathUtil() {
    }

    public static String withoutApiPrefix(String servletPath) {
        if (servletPath.equals(API_PREFIX)) {
            return "/";
        }
        if (servletPath.startsWith(API_PREFIX + "/")) {
            return servletPath.substring(API_PREFIX.length());
        }
        return servletPath;
    }
}
