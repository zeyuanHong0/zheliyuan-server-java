package org.zly.blogbackjava.controller;

import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.zly.blogbackjava.annotation.RequireLogin;
import org.zly.blogbackjava.common.response.ApiResponse;
import org.zly.blogbackjava.dto.tag.request.CreateTagDto;
import org.zly.blogbackjava.dto.tag.request.TagPageQueryDto;
import org.zly.blogbackjava.dto.tag.request.UpdateTagDto;
import org.zly.blogbackjava.dto.tag.response.TagInfoResult;
import org.zly.blogbackjava.dto.tag.response.TagPageListResult;
import org.zly.blogbackjava.dto.tag.response.TagSimpleItem;
import org.zly.blogbackjava.service.TagService;

import java.util.List;

@RestController
@RequestMapping("/tag")
@RequireLogin
public class TagController {

    private final TagService tagService;

    public TagController(TagService tagService) {
        this.tagService = tagService;
    }

    @PostMapping("/createTag")
    @PreAuthorize("hasRole('ADMIN')")
    public ApiResponse<Void> createTag(@RequestBody @Valid CreateTagDto createTagDto) {
        tagService.createTag(createTagDto);
        return ApiResponse.success("创建标签成功", null);
    }

    @GetMapping("/getAllTagList")
    public ApiResponse<List<TagSimpleItem>> getAllTagList() {
        List<TagSimpleItem> list = tagService.getAllTagList();
        return ApiResponse.success(list);
    }

    @GetMapping("/getTagListByPage")
    public ApiResponse<TagPageListResult> getTagListByPage(@Valid TagPageQueryDto tagPageQueryDto) {
        TagPageListResult result = tagService.getTagListByPage(tagPageQueryDto);
        return ApiResponse.success("获取标签列表成功", result);
    }

    @GetMapping("/getTagInfo/{id}")
    public ApiResponse<TagInfoResult> getTagInfo(@PathVariable String id) {
        TagInfoResult tag = tagService.getTagInfo(id);
        return ApiResponse.success("获取标签信息成功", tag);
    }

    @PutMapping("/updateTag")
    @PreAuthorize("hasRole('ADMIN')")
    public ApiResponse<Void> updateTag(@RequestBody @Valid UpdateTagDto updateTagDto) {
        tagService.updateTag(updateTagDto);
        return ApiResponse.success("更新标签成功", null);
    }

    @DeleteMapping("/deleteTag/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ApiResponse<Void> deleteTag(@PathVariable String id) {
        tagService.deleteTag(id);
        return ApiResponse.success("删除标签成功", null);
    }
}
