package org.zly.blogbackjava.repository;

import org.zly.blogbackjava.entity.Category;

import java.util.List;

public interface CategoryRepository {
    void insertCategory(String id, String name);

    List<Category> getCategoryListByPage(String name, Integer offset, Integer pageSize);

    Long countCategoryList(String name);

    Category getCategoryInfo(String id);

    void updateCategory(String id, String name, Integer isDelete);

    List<Category> getAllCategoryList();
}
