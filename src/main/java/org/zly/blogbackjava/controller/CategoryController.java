package org.zly.blogbackjava.controller;


import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import org.zly.blogbackjava.annotation.RequireLogin;
import org.zly.blogbackjava.common.response.ApiResponse;
import org.zly.blogbackjava.dto.request.CategoryPageQueryDto;
import org.zly.blogbackjava.dto.request.CreateCategoryDto;
import org.zly.blogbackjava.dto.response.CategoryPageListResult;
import org.zly.blogbackjava.service.CategoryService;

@RestController
@RequestMapping("/category")
@RequireLogin
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @PostMapping("/createCategory")
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
}
