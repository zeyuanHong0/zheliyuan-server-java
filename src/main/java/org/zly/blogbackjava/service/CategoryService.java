package org.zly.blogbackjava.service;

import org.zly.blogbackjava.dto.response.CategoryPageListResult;

public interface CategoryService {

    void createCategory(String name);

    CategoryPageListResult findByPage(String name, Integer pageNum, Integer pageSize);
}
