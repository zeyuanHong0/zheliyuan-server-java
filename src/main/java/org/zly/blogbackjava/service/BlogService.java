package org.zly.blogbackjava.service;

import org.zly.blogbackjava.dto.blog.request.BlogPageQueryDto;
import org.zly.blogbackjava.dto.blog.request.CreateBlogDto;
import org.zly.blogbackjava.dto.blog.request.UpdateBlogDto;
import org.zly.blogbackjava.dto.blog.response.BlogInfoResult;
import org.zly.blogbackjava.dto.blog.response.BlogPageListResult;
import org.zly.blogbackjava.entity.User;

public interface BlogService {

    BlogPageListResult getBlogPageList(BlogPageQueryDto blogPageQueryDto, User user);

    BlogInfoResult getBlogInfo(String id);

    void createBlog(CreateBlogDto createBlogDto, User user);

    void updateBlog(UpdateBlogDto updateBlogDto,User user);
}
