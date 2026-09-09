package org.zly.blogbackjava.service;

import org.zly.blogbackjava.dto.blog.request.BlogPageQueryDto;
import org.zly.blogbackjava.dto.blog.response.BlogPageListResult;
import org.zly.blogbackjava.entity.User;

public interface BlogService {

    BlogPageListResult getBlogPageList(BlogPageQueryDto blogPageQueryDto, User user);
}
