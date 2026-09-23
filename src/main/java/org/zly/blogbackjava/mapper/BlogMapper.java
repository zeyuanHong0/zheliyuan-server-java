package org.zly.blogbackjava.mapper;

import org.apache.ibatis.annotations.Param;
import org.zly.blogbackjava.dto.blog.response.BlogInfoResult;
import org.zly.blogbackjava.dto.blog.response.BlogPageListItem;
import org.zly.blogbackjava.dto.blog.response.BlogTagListItem;
import org.zly.blogbackjava.entity.Blog;
import org.zly.blogbackjava.entity.Tag;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public interface BlogMapper {
    List<BlogPageListItem> getBlogPageList(
            @Param("title") String title,
            @Param("categoryId") String categoryId,
            @Param("tagIds") List<String> tagIds,
            @Param("userId") String userId,
            @Param("isAdmin") boolean isAdmin,
            @Param("offset") Integer offset,
            @Param("pageSize") Integer pageSize
    );

    Long countBlogPageList(
            @Param("title") String title,
            @Param("categoryId") String categoryId,
            @Param("tagIds") List<String> tagIds,
            @Param("userId") String userId,
            @Param("isAdmin") boolean isAdmin
    );

    Integer countBlogs(@Param("createUser") String createUser);

    Integer countPublishedBlogs(@Param("createUser") String createUser);

    Long countBlogsByRange(
            @Param("startTime") LocalDateTime startTime,
            @Param("endTime") LocalDateTime endTime,
            @Param("createUser") String createUser);

    List<Map<String, Object>> countPublishedBlogsByDate(
            @Param("startTime") LocalDateTime startTime,
            @Param("endTime") LocalDateTime endTime,
            @Param("createUser") String createUser);

    List<Map<String, Object>> getCategoryDistribution(@Param("createUser") String createUser);

    void insertBlog(Blog blog);

    void updateBlog(Blog blog);

    void updateAiSummary(@Param("blogId") String blogId,
                         @Param("aiSummary") String aiSummary);

    List<BlogTagListItem> getTagsByBlogIds(
            @Param("blogIds") List<String> blogIds
    );

    List<Tag> getBlogTags(@Param("blogId") String blogId);

    BlogInfoResult getBlogById(@Param("id") String id);

    void changeBlogStatus(@Param("blogId") String blogId, @Param("published") Integer published);

    void deleteBlog(@Param("blogId") String blogId);
}
