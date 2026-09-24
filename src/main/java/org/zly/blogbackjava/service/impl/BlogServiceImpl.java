package org.zly.blogbackjava.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.zly.blogbackjava.dto.blog.request.BlogPageQueryDto;
import org.zly.blogbackjava.dto.blog.request.CreateBlogDto;
import org.zly.blogbackjava.dto.blog.request.UpdateBlogDto;
import org.zly.blogbackjava.dto.blog.response.BlogInfoResult;
import org.zly.blogbackjava.dto.blog.response.BlogPageListItem;
import org.zly.blogbackjava.dto.blog.response.BlogPageListResult;
import org.zly.blogbackjava.dto.blog.response.BlogTagListItem;
import org.zly.blogbackjava.dto.tag.response.TagSimpleItem;
import org.zly.blogbackjava.entity.Blog;
import org.zly.blogbackjava.entity.Category;
import org.zly.blogbackjava.entity.Tag;
import org.zly.blogbackjava.entity.User;
import org.zly.blogbackjava.enums.Role;
import org.zly.blogbackjava.exception.BusinessException;
import org.zly.blogbackjava.repository.BlogRepository;
import org.zly.blogbackjava.repository.BlogTagRepository;
import org.zly.blogbackjava.repository.CategoryRepository;
import org.zly.blogbackjava.repository.TagRepository;
import org.zly.blogbackjava.repository.UserRepository;
import org.zly.blogbackjava.service.AiService;
import org.zly.blogbackjava.service.BlogService;
import org.zly.blogbackjava.service.UserService;
import org.zly.blogbackjava.util.IdGenerator;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.*;

@Service
public class BlogServiceImpl implements BlogService {

    private final BlogRepository blogRepository;
    private final UserRepository userRepository;
    private final BlogTagRepository blogTagRepository;
    private final CategoryRepository categoryRepository;
    private final TagRepository tagRepository;
    private final AiService aiService;
    private final UserService userService;

    public BlogServiceImpl(BlogRepository blogRepository, UserRepository userRepository,
                           BlogTagRepository blogTagRepository,
                           CategoryRepository categoryRepository, TagRepository tagRepository,
                           AiService aiService, UserService userService) {
        this.blogRepository = blogRepository;
        this.userRepository = userRepository;
        this.blogTagRepository = blogTagRepository;
        this.categoryRepository = categoryRepository;
        this.tagRepository = tagRepository;
        this.aiService = aiService;
        this.userService = userService;
    }

    @Override
    @Transactional(readOnly = true)
    public BlogPageListResult getBlogPageList(BlogPageQueryDto blogPageQueryDto, User user) {
        String title = blogPageQueryDto.getTitle();
        Integer pageNum = blogPageQueryDto.getPageNum();
        Integer pageSize = blogPageQueryDto.getPageSize();
        Integer offset = (pageNum - 1) * pageSize;
        String searchCategoryId = blogPageQueryDto.getSearchCategoryId();

        List<String> tagIds = Arrays.stream(
                        Optional.ofNullable(blogPageQueryDto.getSearchTags()).orElse("").split(","))
                .map(String::trim).filter(tagId -> !tagId.isEmpty()).toList();
        String userId = user != null ? user.getId() : null;
        boolean isAdmin =
                user != null && Objects.equals(user.getAccountType(), Role.ADMIN.getCode());

        List<BlogPageListItem> blogs = blogRepository.getBlogPageList(title, searchCategoryId,
                tagIds, userId, isAdmin, offset, pageSize);
        Long total = blogRepository.countBlogPageList(title, searchCategoryId, tagIds, userId,
                isAdmin);

        if (blogs == null || blogs.isEmpty()) {
            return new BlogPageListResult(List.of(), total, pageNum, pageSize);
        }

        List<String> blogIds = blogs.stream().map(BlogPageListItem::getId).toList();
        List<BlogTagListItem> tagRows = blogRepository.getTagsByBlogIds(blogIds);
        Map<String, List<TagSimpleItem>> tagsByBlogId = new HashMap<>();
        for (BlogTagListItem row : tagRows) {
            String blogId = row.blogId();
            TagSimpleItem tag = new TagSimpleItem(row.tagId(), row.tagName());
            tagsByBlogId.computeIfAbsent(blogId, k -> new ArrayList<>()).add(tag);
        }

        List<BlogPageListItem> blogPageList = blogs.stream()
                .map(blog -> new BlogPageListItem(blog.getId(), blog.getTitle(),
                        blog.getPublished(), tagsByBlogId.getOrDefault(blog.getId(), List.of()),
                        blog.getCategory(), blog.getCreateTime(), blog.getUpdateTime(),
                        blog.getAuthor())

                ).toList();
        return new BlogPageListResult(blogPageList, total, pageNum, pageSize);
    }

    @Override
    @Transactional(readOnly = true)
    public BlogInfoResult getBlogInfo(String id) {
        BlogInfoResult blogInfo = blogRepository.getBlogInfo(id);
        if (blogInfo == null) {
            throw new BusinessException(404, "博客不存在");
        }
        // 查询博客标签
        List<Tag> tags = blogRepository.getBlogTags(id);
        List<TagSimpleItem> tagSimpleList = tags.stream()
                .map(tag -> new TagSimpleItem(tag.getId(), tag.getName())).toList();
        blogInfo.setTags(tagSimpleList);
        return blogInfo;
    }

    @Override
    @Transactional
    public void createBlog(CreateBlogDto createBlogDto, User user) {
        // 验证用户是否存在
        User findUser = userRepository.findById(user.getId());
        if (findUser == null || Integer.valueOf(1).equals(findUser.getIsDelete())) {
            throw new BusinessException(404, "用户不存在或已被删除");
        }
        Category category = categoryRepository.getCategoryInfo(createBlogDto.getCategory());
        if (category == null) {
            throw new BusinessException(404, "分类不存在");
        }
        List<String> tagIds = createBlogDto.getTags();
        if (new HashSet<>(tagIds).size() != tagIds.size()) {
            throw new BusinessException(400, "标签不能重复");
        }
        List<Tag> tags = tagRepository.getTagsByIds(tagIds);
        if (tags.size() != tagIds.size()) {
            throw new BusinessException(404, "部分标签不存在");
        }

        Blog blog = new Blog();
        blog.setTitle(createBlogDto.getTitle());
        blog.setDescription(createBlogDto.getDescription());
        blog.setContent(createBlogDto.getContent());
        blog.setPublished(createBlogDto.getPublished());
        blog.setCategoryId(createBlogDto.getCategory());
        blog.setCreateUser(user.getId());
        String newId = IdGenerator.generateId();
        blog.setId(newId);
        blogRepository.create(blog);
        for (String tagId : tagIds) {
            blogTagRepository.insert(newId, tagId);
        }
        aiService.summarizeBlogAsync(newId, createBlogDto.getContent());
    }

    @Override
    @Transactional
    public void updateBlog(UpdateBlogDto updateBlogDto, User user) {
        String id = updateBlogDto.getId();
        String aiSummary = updateBlogDto.getAiSummary();
        String categoryId = updateBlogDto.getCategory();
        List<String> tagIds = updateBlogDto.getTags();
        // 查询文章
        BlogInfoResult searchBlog = blogRepository.getBlogInfo(updateBlogDto.getId());
        boolean isAdmin = userService.isAdmin(user.getId());
        if (searchBlog == null) {
            throw new BusinessException(404, "博客不存在");
        }
        if (!searchBlog.getCreateUser().equals(user.getId()) && !isAdmin) {
            throw new BusinessException(403, "没有权限修改该博客");
        }

        Category category = categoryRepository.getCategoryInfo(categoryId);
        if (category == null) {
            throw new BusinessException(404, "分类不存在");
        }
        if (new HashSet<>(tagIds).size() != tagIds.size()) {
            throw new BusinessException(400, "标签不能重复");
        }
        List<Tag> tags = tagRepository.getTagsByIds(tagIds);
        if (tags.size() != tagIds.size()) {
            throw new BusinessException(404, "部分标签不存在");
        }

        Blog blog = new Blog();
        blog.setId(id);
        blog.setTitle(updateBlogDto.getTitle());
        blog.setDescription(updateBlogDto.getDescription());
        blog.setContent(updateBlogDto.getContent());
        blog.setPublished(updateBlogDto.getPublished());
        blog.setCategoryId(categoryId);
        if (aiSummary != null && !aiSummary.isBlank()) {
            blog.setAiSummary(aiSummary);
        }
        blogRepository.update(blog);
        // 先删除原有的标签关联
        blogTagRepository.deleteByBlogId(id);
        for (String tagId : tagIds) {
            blogTagRepository.insert(id, tagId);
        }
        // 如果清空了aiSummary，则重新生成
        if (aiSummary == null || aiSummary.isBlank()) {
            aiService.summarizeBlogAsync(id, updateBlogDto.getContent());
        }
    }

    @Override
    public void changeBlogStatus(String blogId, Integer published, User user) {
        BlogInfoResult searchBlog = blogRepository.getBlogInfo(blogId);
        boolean isAdmin = userService.isAdmin(user.getId());
        if (searchBlog == null) {
            throw new BusinessException(404, "博客不存在");
        }
        if (!searchBlog.getCreateUser().equals(user.getId()) && !isAdmin) {
            throw new BusinessException(403, "没有权限修改该博客");
        }
        blogRepository.changeBlogStatus(blogId, published);
    }

    @Override
    public void deleteBlog(String blogId, User user) {
        BlogInfoResult searchBlog = blogRepository.getBlogInfo(blogId);
        boolean isAdmin = userService.isAdmin(user.getId());
        if (searchBlog == null) {
            throw new BusinessException(404, "博客不存在");
        }
        if (!searchBlog.getCreateUser().equals(user.getId()) && !isAdmin) {
            throw new BusinessException(403, "没有权限删除该博客");
        }
        blogRepository.deleteBlog(blogId);
    }

    /**
     * 获取所有博客的数量
     *
     * @param user 当前用户
     * @return 博客总数
     */
    @Override
    public Integer getAllBlogCount(User user) {
        boolean isAdmin = userService.isAdmin(user.getId());
        String userId = isAdmin ? null : user.getId();
        return blogRepository.countBlogs(userId);
    }

    /**
     * 获取已发布博客的数量
     *
     * @param user 当前用户
     * @return 已发布博客总数
     */
    @Override
    public Integer getPublishedBlogCount(User user) {
        boolean isAdmin = userService.isAdmin(user.getId());
        String userId = isAdmin ? null : user.getId();
        return blogRepository.countPublishedBlogs(userId);
    }

    /**
     * 获取每周新增博客的数量
     *
     * @param user 当前用户
     * @return 每周新增博客数量的映射
     */
    @Override
    public Map<String, Object> getWeeklyAddedBlogCount(User user) {
        boolean isAdmin = userService.isAdmin(user.getId());
        String userId = isAdmin ? null : user.getId();

        LocalDateTime currentWeekStart = LocalDateTime.now()
                .with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY))
                .toLocalDate()
                .atStartOfDay();
        LocalDateTime currentWeekEnd = currentWeekStart.plusWeeks(1);
        LocalDateTime lastWeekStart = currentWeekStart.minusWeeks(1);

        Long currentWeekBlogCount = blogRepository.countBlogsByRange(currentWeekStart,
                currentWeekEnd, userId);
        Long lastWeekBlogCount = blogRepository.countBlogsByRange(lastWeekStart,
                currentWeekStart, userId);

        BigDecimal growthRate = lastWeekBlogCount == 0
                ? null
                : BigDecimal.valueOf(
                        (currentWeekBlogCount - lastWeekBlogCount) * 100.0 / lastWeekBlogCount)
                .setScale(2, RoundingMode.HALF_UP);

        Map<String, Object> result = new HashMap<>();
        result.put("currentWeekBlogCount", currentWeekBlogCount);
        result.put("lastWeekBlogCount", lastWeekBlogCount);
        result.put("growthRate", growthRate);
        return result;
    }

    /**
     * 获取最近7天博客发布趋势
     *
     * @param user 当前用户
     * @return 最近7天博客发布趋势的映射
     */
    @Override
    public Map<String, Object> getLast7DaysBlogPublishTrend(User user) {
        boolean isAdmin = userService.isAdmin(user.getId());
        String userId = isAdmin ? null : user.getId();

        LocalDate startDate = LocalDate.now().minusDays(6);
        LocalDateTime startTime = startDate.atStartOfDay();
        LocalDateTime endTime = LocalDate.now().plusDays(1).atStartOfDay();
        List<Map<String, Object>> trendRaw = blogRepository.countPublishedBlogsByDate(
                startTime, endTime, userId);

        Map<String, Long> trendMap = new HashMap<>();
        for (Map<String, Object> row : trendRaw) {
            String date = row.get("date").toString();
            long count = ((Number) row.get("count")).longValue();
            trendMap.put(date, count);
        }

        List<String> dateList = new ArrayList<>();
        List<Long> data = new ArrayList<>();
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("MM-dd");
        for (int i = 0; i < 7; i++) {
            LocalDate date = startDate.plusDays(i);
            dateList.add(date.format(dateFormatter));
            data.add(trendMap.getOrDefault(date.toString(), 0L));
        }

        return Map.of(
                "dateList", dateList,
                "data", data
        );
    }

    @Override
    public List<Map<String, Object>> getCategoryDistribution(User user) {
        boolean isAdmin = userService.isAdmin(user.getId());
        String userId = isAdmin ? null : user.getId();

        List<Map<String, Object>> raw = blogRepository.getCategoryDistribution(userId);
        List<Map<String, Object>> result = new ArrayList<>();
        if (raw != null) {
            for (Map<String, Object> row : raw) {
                result.add(Map.of(
                        "name", row.get("categoryName").toString(),
                        "value", ((Number) row.get("count")).longValue()
                ));
            }
        }
        return result;
    }

    @Override
    public List<Map<String, Object>> getTop5Tags(User user) {
        boolean isAdmin = userService.isAdmin(user.getId());
        String userId = isAdmin ? null : user.getId();

        List<Map<String, Object>> raw = blogRepository.getTopTags(userId, 5);
        List<Map<String, Object>> result = new ArrayList<>();
        if (raw != null) {
            for (Map<String, Object> row : raw) {
                result.add(Map.of(
                        "name", row.get("tagName").toString(),
                        "value", ((Number) row.get("count")).longValue()
                ));
            }
        }
        return result;
    }
}
