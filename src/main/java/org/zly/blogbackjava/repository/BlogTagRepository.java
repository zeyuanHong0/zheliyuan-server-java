package org.zly.blogbackjava.repository;

public interface BlogTagRepository {

    void insert(String blogId, String tagId);

    void deleteByBlogId(String blogId);
}
