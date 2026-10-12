package org.zly.blogbackjava.controller;

import org.springframework.web.bind.annotation.*;
import org.zly.blogbackjava.common.response.ApiResponse;
import org.zly.blogbackjava.dto.blog.response.AllBlogsResult;
import org.zly.blogbackjava.dto.blog.response.BlogInfoResult;
import org.zly.blogbackjava.service.BlogService;

import java.util.List;

@RestController
@RequestMapping("/miniapp/blog")
public class BlogMiniappController {

    private final BlogService blogService;

    public BlogMiniappController(BlogService blogService) {
        this.blogService = blogService;
    }

    @GetMapping("/getLatestBlogList")
    public ApiResponse<List<AllBlogsResult>> getLatestBlogList(
            @RequestParam(name = "num", required = false) Integer num
    ) {
        List<AllBlogsResult> result = blogService.getLatestBlogList(num);
        return ApiResponse.success("获取成功", result);
    }

    @GetMapping("/getAllBlogList")
    public ApiResponse<List<AllBlogsResult>> getAllBlogList(
    ) {
        List<AllBlogsResult> result = blogService.getAllBlogs();
        return ApiResponse.success("获取成功", result);
    }

    @GetMapping("/getBlogInfo/{id}")
    public ApiResponse<BlogInfoResult> getBlogInfo(
            @PathVariable String id
    ) {
        BlogInfoResult result = blogService.getBlogInfo(id);
        return ApiResponse.success("获取成功", result);
    }
}
