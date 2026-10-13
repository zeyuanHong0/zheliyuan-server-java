package org.zly.blogbackjava.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.zly.blogbackjava.common.response.ApiResponse;
import org.zly.blogbackjava.dto.category.response.CategoryFrontInfo;
import org.zly.blogbackjava.dto.category.response.CategoryFrontListItem;
import org.zly.blogbackjava.service.CategoryService;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/miniapp/category")
public class CategoryMiniappController {

    private final CategoryService categoryService;

    public CategoryMiniappController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping("getAllCategoryList")
    public ApiResponse<Map<String, List<CategoryFrontListItem>>> getAllCategoryList() {
        List<CategoryFrontListItem> result = categoryService.getAllCategoryFrontList();
        return ApiResponse.success("获取成功", Map.of("list", result));
    }

    @GetMapping("getCategoryInfo/{id}")
    public ApiResponse<CategoryFrontInfo> getMiniAppCategoryInfo(@PathVariable String id) {
        CategoryFrontInfo result = categoryService.getCategoryFrontInfo(id);
        return ApiResponse.success("获取成功", result);
    }
}
