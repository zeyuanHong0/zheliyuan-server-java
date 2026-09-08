package org.zly.blogbackjava.dto.tag.response;

import java.util.List;

public record TagPageListResult(
        List<TagPageListItem> list,
        Integer pageNum,
        Integer pageSize,
        Long total
) {
}
