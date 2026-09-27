package org.zly.blogbackjava.dto.category.response;

import lombok.Data;

@Data
public class CategoryFrontListItem {
    private String id;
    private String name;
    private Long blogCount;
}
