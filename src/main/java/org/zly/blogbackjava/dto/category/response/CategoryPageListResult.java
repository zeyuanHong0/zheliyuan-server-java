package org.zly.blogbackjava.dto.category.response;

import java.util.List;

public record CategoryPageListResult(
        List<CategoryPageListItem> list,
        Integer pageNum,
        Integer pageSize,
        Long total
) {
}
