package org.zly.blogbackjava.repository.impl;

import org.springframework.stereotype.Component;
import org.zly.blogbackjava.dto.blog.response.BlogInfoResult;
import org.zly.blogbackjava.dto.blog.response.BlogPageListItem;
import org.zly.blogbackjava.dto.blog.response.BlogTagListItem;
import org.zly.blogbackjava.entity.Blog;
import org.zly.blogbackjava.entity.Tag;
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
    public void create(Blog blog){
        blogMapper.insertBlog(blog);
    }

    @Override
    public void update(Blog blog){
        blogMapper.updateBlog(blog);
    }

    @Override
    public void updateAiSummary(String blogId, String aiSummary) {
        blogMapper.updateAiSummary(blogId, aiSummary);
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
    public List<Tag> getBlogTags(String blogId) {
        return blogMapper.getBlogTags(blogId);
    }

    @Override
    public Long countBlogPageList(String title, String categoryId, List<String> tagIds, String userId, boolean isAdmin) {
        return blogMapper.countBlogPageList(title, categoryId, tagIds, userId, isAdmin);
    }

    @Override
    public BlogInfoResult getBlogInfo(String id) {
        return blogMapper.getBlogById(id);
    }
}
