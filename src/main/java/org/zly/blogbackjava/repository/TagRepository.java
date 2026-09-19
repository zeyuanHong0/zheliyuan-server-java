package org.zly.blogbackjava.repository;

import org.zly.blogbackjava.entity.Tag;

import java.util.List;

public interface TagRepository {

    void insertTag(String id, String name, String icon, String iconDark);

    List<Tag> getTagListByPage(String name, Integer offset, Integer pageSize);

    Long countTagList(String name);

    Integer countAllTags();

    List<Tag> getAllTags();

    List<Tag> getTagsByIds(List<String> ids);

    Tag getTagInfo(String id);

    Tag getTagByName(String name);

    void updateTag(String id, String name, String icon, String iconDark, Integer isDelete);
}
