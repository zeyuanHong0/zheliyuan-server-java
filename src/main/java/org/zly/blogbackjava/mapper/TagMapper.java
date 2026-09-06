package org.zly.blogbackjava.mapper;

import org.apache.ibatis.annotations.Param;
import org.zly.blogbackjava.entity.Tag;

import java.util.List;

public interface TagMapper {

    void insertTag(
            @Param("id") String id,
            @Param("name") String name,
            @Param("icon") String icon,
            @Param("iconDark") String iconDark
    );

    List<Tag> getTagListByPage(
            @Param("name") String name,
            @Param("offset") Integer offset,
            @Param("pageSize") Integer pageSize
    );

    Long countTagList(@Param("name") String name);

    List<Tag> getAllTags();

    Tag getTagById(@Param("id") String id);

    void updateTag(
            @Param("id") String id,
            @Param("name") String name,
            @Param("icon") String icon,
            @Param("iconDark") String iconDark,
            @Param("isDelete") Integer isDelete
    );
}
