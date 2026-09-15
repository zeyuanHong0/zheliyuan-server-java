package org.zly.blogbackjava.repository;

import org.zly.blogbackjava.dto.blog.response.BlogInfoResult;
import org.zly.blogbackjava.dto.blog.response.BlogPageListItem;
import org.zly.blogbackjava.dto.blog.response.BlogTagListItem;
import org.zly.blogbackjava.entity.Blog;
import org.zly.blogbackjava.entity.Tag;

import java.util.List;

public interface BlogRepository {

    void create(Blog blog);

    void update(Blog blog);

    void updateAiSummary(String blogId, String aiSummary);

    List<BlogPageListItem> getBlogPageList(String title, String categoryId, List<String> tagIds, String userId, boolean isAdmin, Integer offset, Integer pageSize);

    List<BlogTagListItem> getTagsByBlogIds(List<String> blogIds);

    List<Tag> getBlogTags(String blogId);

    Long countBlogPageList(String title, String categoryId, List<String> tagIds, String userId, boolean isAdmin);

    BlogInfoResult getBlogInfo(String id);
}
