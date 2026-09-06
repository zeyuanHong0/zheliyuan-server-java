package org.zly.blogbackjava.repository.impl;

import org.springframework.stereotype.Component;
import org.zly.blogbackjava.entity.Tag;
import org.zly.blogbackjava.mapper.TagMapper;
import org.zly.blogbackjava.repository.TagRepository;

import java.util.List;

@Component
public class TagRepositoryImpl implements TagRepository {

    private final TagMapper tagMapper;

    public TagRepositoryImpl(TagMapper tagMapper) {
        this.tagMapper = tagMapper;
    }

    @Override
    public void insertTag(String id, String name, String icon, String iconDark) {
        tagMapper.insertTag(id, name, icon, iconDark);
    }

    @Override
    public List<Tag> getTagListByPage(String name, Integer offset, Integer pageSize) {
        return tagMapper.getTagListByPage(name, offset, pageSize);
    }

    @Override
    public Long countTagList(String name) {
        return tagMapper.countTagList(name);
    }

    @Override
    public List<Tag> getAllTags() {
        return tagMapper.getAllTags();
    }

    @Override
    public Tag getTagInfo(String id) {
        return tagMapper.getTagById(id);
    }

    @Override
    public void updateTag(String id, String name, String icon, String iconDark, Integer isDelete) {
        tagMapper.updateTag(id, name, icon, iconDark, isDelete);
    }
}
