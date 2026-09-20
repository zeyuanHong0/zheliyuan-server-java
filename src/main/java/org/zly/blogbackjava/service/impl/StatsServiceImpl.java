package org.zly.blogbackjava.service.impl;

import org.springframework.stereotype.Service;
import org.zly.blogbackjava.entity.User;
import org.zly.blogbackjava.service.BlogService;
import org.zly.blogbackjava.service.CategoryService;
import org.zly.blogbackjava.service.StatsService;
import org.zly.blogbackjava.service.TagService;

import java.util.Map;

@Service
public class StatsServiceImpl implements StatsService {

    private final BlogService blogService;
    private final TagService tagService;
    private final CategoryService categoryService;

    public StatsServiceImpl(BlogService blogService, TagService tagService,
                            CategoryService categoryService) {
        this.blogService = blogService;
        this.tagService = tagService;
        this.categoryService = categoryService;
    }

    @Override
    public void getDashboardStats(User user) {
        Integer allBlogCount = blogService.getAllBlogCount(user);
        Integer publishedBlogCount = blogService.getPublishedBlogCount(user);
        Integer tagCount = tagService.getTagCount();
        Integer categoryCount = categoryService.getCategoryCount();
        Map<String, Object> weeklyAddedBlogCount = blogService.getWeeklyAddedBlogCount(user);
    }
}
