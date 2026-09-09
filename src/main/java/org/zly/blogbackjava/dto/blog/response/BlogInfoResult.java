package org.zly.blogbackjava.dto.blog.response;

import org.zly.blogbackjava.dto.tag.response.TagSimpleItem;

import java.util.List;

public record BlogInfoResult(
        String id,
        String title,
        String description,
        String aiSummary,
        String content,
        Integer published,
        List<TagSimpleItem> tags,
        BlogCategoryItem category
) {
}
