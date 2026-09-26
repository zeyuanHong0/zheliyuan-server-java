package org.zly.blogbackjava.repository;

import org.zly.blogbackjava.dto.blog.response.BlogFrontInfo;
import org.zly.blogbackjava.dto.blog.response.BlogInfoResult;
import org.zly.blogbackjava.dto.blog.response.BlogPageListItem;
import org.zly.blogbackjava.dto.blog.response.BlogTagListItem;
import org.zly.blogbackjava.entity.Blog;
import org.zly.blogbackjava.entity.Tag;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public interface BlogRepository {

    void create(Blog blog);

    void update(Blog blog);

    void updateAiSummary(String blogId, String aiSummary);

    List<BlogPageListItem> getBlogPageList(String title, String categoryId, List<String> tagIds, String userId, boolean isAdmin, Integer offset, Integer pageSize);

    List<BlogTagListItem> getTagsByBlogIds(List<String> blogIds);

    List<Tag> getBlogTags(String blogId);

    Long countBlogPageList(String title, String categoryId, List<String> tagIds, String userId, boolean isAdmin);

    Integer countBlogs(String createUser);

    Integer countPublishedBlogs(String createUser);

    Long countBlogsByRange(LocalDateTime startTime, LocalDateTime endTime, String createUser);

    List<Map<String, Object>> countPublishedBlogsByDate(
            LocalDateTime startTime, LocalDateTime endTime, String createUser);

    List<Map<String, Object>> getCategoryDistribution(String createUser);

    List<Map<String, Object>> getTopTags(String createUser, Integer limit);

    BlogInfoResult getBlogInfo(String id);

    void changeBlogStatus(String blogId, Integer published);

    void deleteBlog(String blogId);

    BlogFrontInfo getFrontInfo(String id);
}
