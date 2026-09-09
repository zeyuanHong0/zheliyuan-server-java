package org.zly.blogbackjava.repository.impl;

import org.springframework.stereotype.Component;
import org.zly.blogbackjava.dto.blog.response.BlogPageListItem;
import org.zly.blogbackjava.dto.blog.response.BlogTagListItem;
import org.zly.blogbackjava.mapper.BlogMapper;
import org.zly.blogbackjava.repository.BlogRepository;

import java.util.List;

@Component
public class BlogRepositoryImpl implements BlogRepository {

    private final BlogMapper blogMapper;

    public BlogRepositoryImpl(BlogMapper blogMapper) {
        this.blogMapper = blogMapper;
    }

    @Override
    public List<BlogPageListItem> getBlogPageList(String title, String categoryId, List<String> tagIds, String userId, boolean isAdmin, Integer offset, Integer pageSize) {
        return blogMapper.getBlogPageList(title, categoryId, tagIds, userId, isAdmin, offset, pageSize);
    }

    @Override
    public List<BlogTagListItem> getTagsByBlogIds(List<String> blogIds) {
        return blogMapper.getTagsByBlogIds(blogIds);
    }

    @Override
    public Long countBlogPageList(String title, String categoryId, List<String> tagIds, String userId, boolean isAdmin) {
        return blogMapper.countBlogPageList(title, categoryId, tagIds, userId, isAdmin);
    }
}
