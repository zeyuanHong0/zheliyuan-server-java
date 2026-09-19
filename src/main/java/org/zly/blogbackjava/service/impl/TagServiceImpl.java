package org.zly.blogbackjava.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.zly.blogbackjava.dto.tag.request.CreateTagDto;
import org.zly.blogbackjava.dto.tag.request.TagPageQueryDto;
import org.zly.blogbackjava.dto.tag.request.UpdateTagDto;
import org.zly.blogbackjava.dto.tag.response.TagPageListItem;
import org.zly.blogbackjava.dto.tag.response.TagPageListResult;
import org.zly.blogbackjava.dto.tag.response.TagSimpleItem;
import org.zly.blogbackjava.dto.tag.response.TagInfoResult;
import org.zly.blogbackjava.entity.Tag;
import org.zly.blogbackjava.exception.BusinessException;
import org.zly.blogbackjava.repository.TagRepository;
import org.zly.blogbackjava.service.TagService;
import org.zly.blogbackjava.util.IdGenerator;

import java.util.List;

@Service
public class TagServiceImpl implements TagService {

    private final TagRepository tagRepository;

    public TagServiceImpl(TagRepository tagRepository) {
        this.tagRepository = tagRepository;
    }

    @Override
    public void createTag(CreateTagDto createTagDto) {
        String name = createTagDto.getName().trim();
        ensureNameAvailable(name, null);
        String id = IdGenerator.generateId();
        String icon = createTagDto.getIcon();
        String iconDark = createTagDto.getIconDark();
        tagRepository.insertTag(id, name, icon, iconDark);
    }

    @Override
    @Transactional(readOnly = true)
    public TagPageListResult getTagListByPage(TagPageQueryDto tagPageQueryDto) {
        String name = tagPageQueryDto.getName();
        Integer pageNum = tagPageQueryDto.getPageNum();
        Integer pageSize = tagPageQueryDto.getPageSize();
        int offset = Math.toIntExact((long) (pageNum - 1) * pageSize);
        List<TagPageListItem> list = tagRepository.getTagListByPage(name, offset, pageSize).stream()
                .map(tag -> new TagPageListItem(
                        tag.getId(),
                        tag.getName(),
                        nullToEmpty(tag.getIcon()),
                        nullToEmpty(tag.getIconDark()),
                        tag.getCreateTime(),
                        tag.getUpdateTime()
                ))
                .toList();
        Long total = tagRepository.countTagList(name);
        return new TagPageListResult(list, pageNum, pageSize, total);
    }

    @Override
    public void updateTag(UpdateTagDto updateTagDto) {
        String id = updateTagDto.getId();
        Tag tag = requireTag(id);
        String name = updateTagDto.getName().trim();
        ensureNameAvailable(name, tag.getId());
        String icon = updateTagDto.getIcon();
        String iconDark = updateTagDto.getIconDark();
        tagRepository.updateTag(id, name, icon, iconDark, null);
    }

    @Override
    public TagInfoResult getTagInfo(String id) {
        Tag tag = requireTag(id);
        return new TagInfoResult(
                tag.getId(),
                tag.getName(),
                nullToEmpty(tag.getIcon()),
                nullToEmpty(tag.getIconDark()),
                tag.getCreateTime(),
                tag.getUpdateTime()
        );
    }

    @Override
    public Integer getTagCount() {
        return tagRepository.countAllTags();
    }

    @Override
    public List<TagSimpleItem> getAllTagList() {
        List<Tag> allTags = tagRepository.getAllTags();
        return allTags.stream()
                .map(tag -> new TagSimpleItem(tag.getId(), tag.getName()))
                .toList();
    }

    @Override
    public void deleteTag(String id) {
        requireTag(id);
        tagRepository.updateTag(id, null, null, null, 1);
    }

    private Tag requireTag(String id) {
        Tag tag = tagRepository.getTagInfo(id);
        if (tag == null) {
            throw new BusinessException(404, "标签不存在");
        }
        return tag;
    }

    private void ensureNameAvailable(String name, String currentTagId) {
        Tag tag = tagRepository.getTagByName(name);
        if (tag != null && !tag.getId().equals(currentTagId)) {
            throw new BusinessException(409, "标签名称已存在");
        }
    }

    private String nullToEmpty(String value) {
        return value == null ? "" : value;
    }
}
