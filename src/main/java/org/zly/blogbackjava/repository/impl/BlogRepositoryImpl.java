package org.zly.blogbackjava.repository.impl;

import org.springframework.stereotype.Component;
import org.zly.blogbackjava.dto.blog.response.*;
import org.zly.blogbackjava.entity.Blog;
import org.zly.blogbackjava.entity.Tag;
import org.zly.blogbackjava.mapper.BlogMapper;
import org.zly.blogbackjava.repository.BlogRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Component
public class BlogRepositoryImpl implements BlogRepository {

    private final BlogMapper blogMapper;

    public BlogRepositoryImpl(BlogMapper blogMapper) {
        this.blogMapper = blogMapper;
    }

    @Override
    public void create(Blog blog) {
        blogMapper.insertBlog(blog);
    }

    @Override
    public void update(Blog blog) {
        blogMapper.updateBlog(blog);
    }

    @Override
    public void updateAiSummary(String blogId, String aiSummary) {
        blogMapper.updateAiSummary(blogId, aiSummary);
    }

    @Override
    public List<BlogPageListItem> getBlogPageList(String title, String categoryId,
                                                  List<String> tagIds, String userId,
                                                  boolean isAdmin, Integer offset,
                                                  Integer pageSize) {
        return blogMapper.getBlogPageList(title, categoryId, tagIds, userId, isAdmin, offset,
                pageSize);
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
    public Long countBlogPageList(String title, String categoryId, List<String> tagIds,
                                  String userId, boolean isAdmin) {
        return blogMapper.countBlogPageList(title, categoryId, tagIds, userId, isAdmin);
    }

    @Override
    public Integer countBlogs(String createUser) {
        return blogMapper.countBlogs(createUser);
    }

    @Override
    public Integer countPublishedBlogs(String createUser) {
        return blogMapper.countPublishedBlogs(createUser);
    }

    @Override
    public Long countBlogsByRange(LocalDateTime startTime, LocalDateTime endTime,
                                  String createUser) {
        return blogMapper.countBlogsByRange(startTime, endTime, createUser);
    }

    @Override
    public List<Map<String, Object>> countPublishedBlogsByDate(LocalDateTime startTime,
                                                                LocalDateTime endTime,
                                                                String createUser) {
        return blogMapper.countPublishedBlogsByDate(startTime, endTime, createUser);
    }

    @Override
    public List<Map<String, Object>> getCategoryDistribution(String createUser) {
        return blogMapper.getCategoryDistribution(createUser);
    }

    @Override
    public List<Map<String, Object>> getTopTags(String createUser, Integer limit) {
        return blogMapper.getTopTags(createUser, limit);
    }

    @Override
    public BlogInfoResult getBlogInfo(String id) {
        return blogMapper.getBlogById(id);
    }

    @Override
    public void changeBlogStatus(String blogId, Integer published) {
        blogMapper.changeBlogStatus(blogId, published);
    }

    @Override
    public void deleteBlog(String blogId) {
        blogMapper.deleteBlog(blogId);
    }

    @Override
    public BlogFrontInfo getFrontInfo(String id) {
        return blogMapper.getFrontInfo(id);
    }

    @Override
    public List<AllBlogsResult> getAllBlogs() {
        return blogMapper.getAllBlogs();
    }
}
