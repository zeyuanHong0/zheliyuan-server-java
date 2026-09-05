package org.zly.blogbackjava.service;

import org.zly.blogbackjava.dto.response.CategoryInfoResult;
import org.zly.blogbackjava.dto.response.CategoryPageListResult;

public interface CategoryService {

    void createCategory(String name);

    CategoryPageListResult findByPage(String name, Integer pageNum, Integer pageSize);

    CategoryInfoResult getCategoryInfo(String id);

    void updateCategory(String id, String name);

    void deleteCategory(String id);
}
