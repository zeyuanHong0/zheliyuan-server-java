package org.zly.blogbackjava.repository.impl;

import org.springframework.stereotype.Component;
import org.zly.blogbackjava.mapper.BlogTagMapper;
import org.zly.blogbackjava.repository.BlogTagRepository;

@Component
public class BlogTagRepositoryImpl implements BlogTagRepository {

    private final BlogTagMapper blogTagMapper;

    public BlogTagRepositoryImpl(BlogTagMapper blogTagMapper) {
        this.blogTagMapper = blogTagMapper;
    }

    @Override
    public void insert(String blogId, String tagId) {
        blogTagMapper.insert(blogId, tagId);
    }

    @Override
    public void deleteByBlogId(String blogId) {
        blogTagMapper.deleteByBlogId(blogId);
    }
}
