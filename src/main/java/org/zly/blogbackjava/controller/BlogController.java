package org.zly.blogbackjava.controller;

import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.zly.blogbackjava.annotation.RequireLogin;
import org.zly.blogbackjava.common.response.ApiResponse;
import org.zly.blogbackjava.dto.blog.request.BlogPageQueryDto;
import org.zly.blogbackjava.dto.blog.request.CreateBlogDto;
import org.zly.blogbackjava.dto.blog.request.UpdateBlogDto;
import org.zly.blogbackjava.dto.blog.response.BlogInfoResult;
import org.zly.blogbackjava.dto.blog.response.BlogPageListResult;
import org.zly.blogbackjava.entity.User;
import org.zly.blogbackjava.service.BlogService;

@RestController
@RequestMapping("/blog")
@RequireLogin
public class BlogController {

    private final BlogService blogService;

    public BlogController(BlogService blogService) {
        this.blogService = blogService;
    }

    @PostMapping("/createBlog")
    public ApiResponse<Void> createBlog(@RequestBody @Valid CreateBlogDto createBlogDto,
                                        @AuthenticationPrincipal User user) {
        blogService.createBlog(createBlogDto, user);
        return ApiResponse.success("创建成功", null);
    }

    @GetMapping("/getBlogListByPage")
    public ApiResponse<BlogPageListResult> getBlogListByPage(
            @Valid BlogPageQueryDto blogPageQueryDto,
            @AuthenticationPrincipal User user
    ) {
        return ApiResponse.success("获取成功", blogService.getBlogPageList(blogPageQueryDto, user));
    }

    @GetMapping("/getBlogInfo/{id}")
    public ApiResponse<BlogInfoResult> getBlogInfo(@PathVariable String id) {
        return ApiResponse.success("获取成功", blogService.getBlogInfo(id));
    }

    @PutMapping("/updateBlog")
    public ApiResponse<Void> updateBlog(@RequestBody @Valid UpdateBlogDto updateBlogDto,
                           @AuthenticationPrincipal User user) {
        blogService.updateBlog(updateBlogDto, user);
        return ApiResponse.success("更新成功", null);
    }

    @PostMapping("/changeBlogStatus/{id}")
    public void changeBlogStatus(@PathVariable String id) {
    }

    @DeleteMapping("/deleteBlog/{id}")
    public void deleteBlog(@PathVariable String id) {
    }
}
