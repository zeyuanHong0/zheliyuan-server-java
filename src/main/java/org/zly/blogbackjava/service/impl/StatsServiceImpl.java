package org.zly.blogbackjava.service.impl;

import org.springframework.stereotype.Service;
import org.zly.blogbackjava.entity.User;
import org.zly.blogbackjava.service.BlogService;
import org.zly.blogbackjava.service.CategoryService;
import org.zly.blogbackjava.service.StatsService;
import org.zly.blogbackjava.service.TagService;

import java.util.List;
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
    public Map<String, Object> getDashboardStats(User user) {
        Integer allBlogCount = blogService.getAllBlogCount(user);
        Integer publishedBlogCount = blogService.getPublishedBlogCount(user);
        Integer tagCount = tagService.getTagCount();
        Integer categoryCount = categoryService.getCategoryCount();
        Map<String, Object> weeklyAddedBlogCount = blogService.getWeeklyAddedBlogCount(user);
        Map<String, Object> blogPublishTrend = blogService.getLast7DaysBlogPublishTrend(user);
        List<Map<String, Object>> categoryDistribution = blogService.getCategoryDistribution(user);
        List<Map<String, Object>> tagDistribution = blogService.getTop5Tags(user);

        return Map.of(
                "allBlogCount", allBlogCount,
                "publishedBlogCount", publishedBlogCount,
                "tagCount", tagCount,
                "categoryCount", categoryCount,
                "weeklyAddedBlogCount", weeklyAddedBlogCount,
                "blogPublishTrend", blogPublishTrend,
                "categoryDistribution", categoryDistribution,
                "tagDistribution", tagDistribution
        );
    }

    @Override
    public List<Integer> getBlogStats() {
        Integer blogCount = blogService.getPublishedBlogCount(null);
        Integer tagCount = tagService.getTagCount();
        Integer categoryCount = categoryService.getCategoryCount();
        return List.of(blogCount, tagCount, categoryCount);
    }
}
