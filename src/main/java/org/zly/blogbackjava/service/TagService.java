package org.zly.blogbackjava.service;

import org.zly.blogbackjava.dto.tag.request.CreateTagDto;
import org.zly.blogbackjava.dto.tag.request.TagPageQueryDto;
import org.zly.blogbackjava.dto.tag.request.UpdateTagDto;
import org.zly.blogbackjava.dto.tag.response.TagPageListResult;
import org.zly.blogbackjava.dto.tag.response.TagSimpleItem;
import org.zly.blogbackjava.dto.tag.response.TagInfoResult;

import java.util.List;

public interface TagService {
    TagPageListResult getTagListByPage(TagPageQueryDto tagPageQueryDto);

    void createTag(CreateTagDto createTagDto);

    void updateTag(UpdateTagDto updateTagDto);

    void deleteTag(String id);

    TagInfoResult getTagInfo(String id);

    Integer getTagCount();

    List<TagSimpleItem> getAllTagList();
}
