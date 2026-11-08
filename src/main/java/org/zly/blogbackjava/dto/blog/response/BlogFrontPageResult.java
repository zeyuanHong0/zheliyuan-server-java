package org.zly.blogbackjava.dto.blog.response;

import java.util.List;

public record BlogFrontPageResult(
        List<AllBlogsResult> list,
        Long total,
        Integer pageNum,
        Integer pageSize
) {
}
