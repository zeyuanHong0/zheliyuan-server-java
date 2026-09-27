package org.zly.blogbackjava.dto.blog.response;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class AllBlogsResult {
    private String id;
    private String title;
    private String description;
    private LocalDateTime createTime;

    private SimpleItem author;
    private SimpleItem category;
    private List<SimpleItem> tags;

    @Data
    public static class SimpleItem {
        private String id;
        private String name;
    }
}
