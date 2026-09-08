package org.zly.blogbackjava.controller;


import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.zly.blogbackjava.annotation.RequireLogin;
import org.zly.blogbackjava.common.response.ApiResponse;
import org.zly.blogbackjava.dto.category.request.CategoryPageQueryDto;
import org.zly.blogbackjava.dto.category.request.CreateCategoryDto;
import org.zly.blogbackjava.dto.category.request.UpdateCategoryDto;
import org.zly.blogbackjava.dto.category.response.CategoryInfoResult;
import org.zly.blogbackjava.dto.category.response.CategoryPageListResult;
import org.zly.blogbackjava.service.CategoryService;

import java.util.Map;

@RestController
@RequestMapping("/category")
@RequireLogin
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @PostMapping("/createCategory")
    @PreAuthorize("hasRole('ADMIN')")
    public ApiResponse<Void> createCategory(@Valid @RequestBody CreateCategoryDto createCategoryDto) {
        categoryService.createCategory(createCategoryDto.getName());
        return ApiResponse.success("创建分类成功", null);
    }

    @GetMapping("/getCategoryListByPage")
    public ApiResponse<CategoryPageListResult> getCategoryListByPage(@Valid CategoryPageQueryDto query) {
        CategoryPageListResult result = categoryService.findByPage(
                query.getName(),
                query.getPageNum(),
                query.getPageSize()
        );
        return ApiResponse.success("获取分类列表成功", result);
    }

    @GetMapping("/getCategoryInfo/{id}")
    public ApiResponse<CategoryInfoResult> getCategoryInfo(@PathVariable String id) {
        CategoryInfoResult categoryInfo = categoryService.getCategoryInfo(id);
        return ApiResponse.success("获取分类信息成功", categoryInfo);
    }

    @PutMapping("/updateCategory")
    @PreAuthorize("hasRole('ADMIN')")
    public ApiResponse<Void> updateCategory(@Valid @RequestBody UpdateCategoryDto updateCategoryDto) {
        categoryService.updateCategory(updateCategoryDto.getId(), updateCategoryDto.getName());
        return ApiResponse.success("更新分类成功", null);
    }

    @DeleteMapping("/deleteCategory/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ApiResponse<Void> deleteCategory(@PathVariable String id) {
        categoryService.deleteCategory(id);
        return ApiResponse.success("删除分类成功", null);
    }
}
