package org.zly.blogbackjava.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.zly.blogbackjava.dto.blog.request.BlogPageQueryDto;
import org.zly.blogbackjava.dto.blog.request.CreateBlogDto;
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
import org.zly.blogbackjava.util.IdGenerator;

import java.util.*;

@Service
public class BlogServiceImpl implements BlogService {

    private final BlogRepository blogRepository;
    private final UserRepository userRepository;
    private final BlogTagRepository blogTagRepository;
    private final CategoryRepository categoryRepository;
    private final TagRepository tagRepository;
    private final AiService aiService;

    public BlogServiceImpl(BlogRepository blogRepository, UserRepository userRepository,
                           BlogTagRepository blogTagRepository,
                           CategoryRepository categoryRepository,
                           TagRepository tagRepository, AiService aiService) {
        this.blogRepository = blogRepository;
        this.userRepository = userRepository;
        this.blogTagRepository = blogTagRepository;
        this.categoryRepository = categoryRepository;
        this.tagRepository = tagRepository;
        this.aiService = aiService;
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
        blogRepository.save(blog);
        for (String tagId : tagIds) {
            blogTagRepository.insert(newId, tagId);
        }
        String summary = aiService.summarizeBlog(createBlogDto.getContent());
        System.out.println("AI生成的摘要: " + summary);
    }
}
