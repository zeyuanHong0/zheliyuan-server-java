package org.zly.blogbackjava.dto.blog.response;

public record BlogTagListItem(
        String blogId,
        String tagId,
        String tagName
) {
}
