package org.zly.blogbackjava.service;

import org.zly.blogbackjava.dto.blog.request.BlogPageQueryDto;
import org.zly.blogbackjava.dto.blog.request.CreateBlogDto;
import org.zly.blogbackjava.dto.blog.request.UpdateBlogDto;
import org.zly.blogbackjava.dto.blog.response.BlogInfoResult;
import org.zly.blogbackjava.dto.blog.response.BlogPageListResult;
import org.zly.blogbackjava.entity.User;

import java.util.List;
import java.util.Map;

public interface BlogService {

    BlogPageListResult getBlogPageList(BlogPageQueryDto blogPageQueryDto, User user);

    BlogInfoResult getBlogInfo(String id);

    void createBlog(CreateBlogDto createBlogDto, User user);

    void updateBlog(UpdateBlogDto updateBlogDto, User user);

    void changeBlogStatus(String blogId, Integer published, User user);

    void deleteBlog(String blogId, User user);

    Integer getAllBlogCount(User user);

    Integer getPublishedBlogCount(User user);

    Map<String, Object> getWeeklyAddedBlogCount(User user);

    Map<String, Object> getLast7DaysBlogPublishTrend(User user);

    List<Map<String, Object>> getCategoryDistribution(User user);

    List<Map<String, Object>> getTop5Tags(User user);
}
