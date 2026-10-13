package org.zly.blogbackjava.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.zly.blogbackjava.common.response.ApiResponse;
import org.zly.blogbackjava.dto.tag.response.TagFrontInfo;
import org.zly.blogbackjava.dto.tag.response.TagFrontListItem;
import org.zly.blogbackjava.service.TagService;

import java.util.List;

@RestController
@RequestMapping("/miniapp/tag")
public class TagMiniappController {

    private final TagService tagService;

    public TagMiniappController(TagService tagService) {
        this.tagService = tagService;
    }

    @GetMapping("/getAllTags")
    public ApiResponse<List<TagFrontListItem>> getAllTags() {
        List<TagFrontListItem> result = tagService.getAllTagsFrontList();
        return ApiResponse.success("获取成功", result);
    }

    @GetMapping("/getTagInfo/{id}")
    public ApiResponse<TagFrontInfo> getTagInfo(@PathVariable String id) {
        TagFrontInfo result = tagService.getTagFrontInfo(id);
        return ApiResponse.success("获取成功", result);
    }
}
