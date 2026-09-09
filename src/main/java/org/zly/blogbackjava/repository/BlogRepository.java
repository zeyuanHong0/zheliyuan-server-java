package org.zly.blogbackjava.repository;

import org.zly.blogbackjava.dto.blog.response.BlogPageListItem;
import org.zly.blogbackjava.dto.blog.response.BlogTagListItem;

import java.util.List;

public interface BlogRepository {

    List<BlogPageListItem> getBlogPageList(String title, String categoryId, List<String> tagIds, String userId, boolean isAdmin, Integer offset, Integer pageSize);

    List<BlogTagListItem> getTagsByBlogIds(List<String> blogIds);

    Long countBlogPageList(String title, String categoryId, List<String> tagIds, String userId, boolean isAdmin);
}
