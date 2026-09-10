package org.zly.blogbackjava.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.zly.blogbackjava.dto.blog.request.BlogPageQueryDto;
import org.zly.blogbackjava.dto.blog.response.BlogPageListItem;
import org.zly.blogbackjava.dto.blog.response.BlogPageListResult;
import org.zly.blogbackjava.dto.blog.response.BlogTagListItem;
import org.zly.blogbackjava.dto.tag.response.TagSimpleItem;
import org.zly.blogbackjava.entity.User;
import org.zly.blogbackjava.enums.Role;
import org.zly.blogbackjava.repository.BlogRepository;
import org.zly.blogbackjava.service.BlogService;

import java.util.*;

@Service
public class BlogServiceImpl implements BlogService {

    private final BlogRepository blogRepository;

    public BlogServiceImpl(BlogRepository blogRepository) {
        this.blogRepository = blogRepository;
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
                        Optional.ofNullable(blogPageQueryDto.getSearchTags()).orElse("").split(",")
                )
                .map(String::trim)
                .filter(tagId -> !tagId.isEmpty())
                .toList();
        String userId = user != null ? user.getId() : null;
        boolean isAdmin =
                user != null && Objects.equals(user.getAccountType(), Role.ADMIN.getCode());

        List<BlogPageListItem> blogs = blogRepository.getBlogPageList(title,
                searchCategoryId, tagIds, userId,
                isAdmin, offset, pageSize);
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
                .map(blog -> new BlogPageListItem(
                                blog.getId(),
                                blog.getTitle(),
                                blog.getPublished(),
                                tagsByBlogId.getOrDefault(blog.getId(), List.of()),
                                blog.getCategory(),
                                blog.getCreateTime(),
                                blog.getUpdateTime(),
                                blog.getAuthor()
                        )

                ).toList();
        return new BlogPageListResult(blogPageList, total, pageNum, pageSize);
    }
}
