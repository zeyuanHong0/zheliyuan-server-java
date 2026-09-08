package org.zly.blogbackjava.validation;

import io.github.borewit.sanitize.SVGSanitizer;

public final class SvgSanitizer {

    private SvgSanitizer() {
    }

    public static String sanitize(String svg) {
        if (svg == null) {
            return null;
        }
        if (svg.isEmpty()) {
            return "";
        }

        try {
            return SVGSanitizer.sanitize(svg);
        } catch (Exception e) {
            throw new IllegalArgumentException("SVG 格式不合法", e);
        }
    }
}
