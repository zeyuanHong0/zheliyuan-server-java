package org.zly.blogbackjava.dto.blog.response;

import lombok.Data;
import org.zly.blogbackjava.dto.tag.response.TagSimpleItem;

import java.util.List;

@Data
public class BlogInfoResult {
    private String id;
    private String title;
    private String description;
    private String aiSummary;
    private String content;
    private Integer published;
    private String createUser;
    private List<TagSimpleItem> tags;
    private BlogCategoryItem category;
}
