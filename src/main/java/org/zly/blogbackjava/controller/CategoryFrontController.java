package org.zly.blogbackjava.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.zly.blogbackjava.common.response.ApiResponse;
import org.zly.blogbackjava.dto.category.response.CategoryFrontInfo;
import org.zly.blogbackjava.dto.category.response.CategoryFrontListItem;
import org.zly.blogbackjava.dto.category.response.CategoryListResult;
import org.zly.blogbackjava.service.CategoryService;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/front/category")
public class CategoryFrontController {

    private final CategoryService categoryService;

    public CategoryFrontController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping("/getAllCategoryList")
    public ApiResponse<Map<String, List<CategoryFrontListItem>>> getAllCategoryList() {
        List<CategoryFrontListItem> categoryList = categoryService.getAllCategoryFrontList();
        return ApiResponse.success("获取成功", Map.of("list", categoryList));
    }

    @GetMapping("/getCategoryInfo/{id}")
    public ApiResponse<CategoryFrontInfo> getCategoryInfo(@PathVariable("id") String id) {
        CategoryFrontInfo categoryInfo = categoryService.getCategoryFrontInfo(id);
        return ApiResponse.success("获取成功", categoryInfo);
    }

    @GetMapping("/getCategorySelectList")
    public ApiResponse<List<CategoryListResult>> getCategorySelectList() {
        List<CategoryListResult> result = categoryService.getAllCategoryList();
        return ApiResponse.success("获取所有分类列表成功", result);
    }
}
