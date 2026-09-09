package org.zly.blogbackjava.dto.blog.response;

import java.util.List;

public record BlogPageListResult(
        List<BlogPageListItem> list,
        Long total,
        Integer pageNum,
        Integer pageSize
) {
}
