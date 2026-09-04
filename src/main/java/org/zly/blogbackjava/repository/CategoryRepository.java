package org.zly.blogbackjava.repository;

import org.zly.blogbackjava.entity.Category;

import java.util.List;

public interface CategoryRepository {
    void insertCategory(String id, String name);

    List<Category> getCategoryListByPage(String name, Integer offset, Integer pageSize);

    Long countCategoryList(String name);
}
