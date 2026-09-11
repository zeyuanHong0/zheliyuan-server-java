package org.zly.blogbackjava.mapper;

import org.apache.ibatis.annotations.Param;
import org.zly.blogbackjava.dto.blog.response.BlogInfoResult;
import org.zly.blogbackjava.dto.blog.response.BlogPageListItem;
import org.zly.blogbackjava.dto.blog.response.BlogTagListItem;
import org.zly.blogbackjava.entity.Blog;

import java.util.List;

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

    List<BlogTagListItem> getTagsByBlogIds(
            @Param("blogIds") List<String> blogIds
    );

    BlogInfoResult getBlogById(@Param("id") String id);
}
