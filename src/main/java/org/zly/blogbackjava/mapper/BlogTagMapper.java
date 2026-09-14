package org.zly.blogbackjava.mapper;

import org.apache.ibatis.annotations.Param;

public interface BlogTagMapper {

    void insert(@Param("blogId") String blogId, @Param("tagId") String tagId);

    void deleteByBlogId(String blogId);
}
