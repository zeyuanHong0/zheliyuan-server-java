package org.zly.blogbackjava.dto.blog.response;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class BlogFrontInfo {
    private String id;

    private String title;

    private String description;

    private String content;

    private String aiSummary;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

    private List<TagItem> tags;

    private SimpleItem category;

    private SimpleItem author;

    @Data
    public static class TagItem {
        private String id;
        private String name;
        private String icon;
        private String icon_dark;
    }

    @Data
    public static class SimpleItem {
        private String id;
        private String name;
    }
}
