package org.zly.blogbackjava.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.zly.blogbackjava.common.response.ApiResponse;
import org.zly.blogbackjava.dto.blog.response.AllBlogsResult;
import org.zly.blogbackjava.dto.blog.response.ArchivesItem;
import org.zly.blogbackjava.dto.blog.response.BlogFrontInfo;
import org.zly.blogbackjava.service.BlogService;

import java.util.List;

@RestController
@RequestMapping("/front/blog")
public class BlogFrontController {

    private final BlogService blogService;

    public BlogFrontController(BlogService blogService) {
        this.blogService = blogService;
    }

    @GetMapping("/getBlogInfo/{id}")
    public ApiResponse<BlogFrontInfo> getBlogInfo(@PathVariable String id) {
        BlogFrontInfo info = blogService.getFrontInfo(id);
        return ApiResponse.success("获取成功", info);
    }

    @GetMapping("/getAllBlogs")
    public ApiResponse<List<AllBlogsResult>> getAllBlogs() {
        List<AllBlogsResult> allBlogs = blogService.getAllBlogs();
        return ApiResponse.success("获取成功", allBlogs);
    }

    @GetMapping("/getArchives")
    public ApiResponse<List<ArchivesItem>> getArchives() {
        List<ArchivesItem> archives = blogService.getArchives();
        return ApiResponse.success("获取成功", archives);
    }
}
