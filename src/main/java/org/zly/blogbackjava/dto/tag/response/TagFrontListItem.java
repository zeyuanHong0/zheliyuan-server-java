package org.zly.blogbackjava.dto.tag.response;


import lombok.Data;

@Data
public class TagFrontListItem {
    private String id;

    private String name;

    private String icon;

    private String icon_dark;

    private Long blogCount;
}
