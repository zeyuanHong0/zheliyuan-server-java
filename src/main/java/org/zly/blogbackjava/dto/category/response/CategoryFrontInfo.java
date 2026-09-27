package org.zly.blogbackjava.dto.category.response;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class CategoryFrontInfo {
    private String id;
    private String name;

    private List<BlogItem> blogs;

    @Data
    public static class BlogItem {
        private String id;
        private String title;
        private String description;
        private LocalDateTime createTime;
        private LocalDateTime updateTime;

        private SimpleItem author;
        private List<SimpleItem> tags;
    }

    @Data
    public static class SimpleItem {
        private String id;
        private String name;
    }
}
