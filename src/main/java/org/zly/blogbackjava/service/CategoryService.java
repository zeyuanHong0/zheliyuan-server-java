package org.zly.blogbackjava.service;

import org.zly.blogbackjava.dto.category.response.CategoryInfoResult;
import org.zly.blogbackjava.dto.category.response.CategoryListResult;
import org.zly.blogbackjava.dto.category.response.CategoryPageListResult;
import org.zly.blogbackjava.entity.Category;

import java.util.List;

public interface CategoryService {

    void createCategory(String name);

    CategoryPageListResult findByPage(String name, Integer pageNum, Integer pageSize);

    CategoryInfoResult getCategoryInfo(String id);

    Integer getCategoryCount();

    void updateCategory(String id, String name);

    void deleteCategory(String id);

    List<CategoryListResult> getAllCategoryList();
}
