package org.zly.blogbackjava.dto.blog.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.zly.blogbackjava.dto.tag.response.TagSimpleItem;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BlogPageListItem {
    private String id;
    private String title;
    private Integer published;
    private List<TagSimpleItem> tags;
    private BlogCategoryItem category;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
    private BlogAuthorItem author;
}
