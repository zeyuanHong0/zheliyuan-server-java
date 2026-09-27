package org.zly.blogbackjava.repository;

import org.zly.blogbackjava.dto.category.response.CategoryFrontListItem;
import org.zly.blogbackjava.entity.Category;

import java.util.List;

public interface CategoryRepository {
    void insertCategory(String id, String name);

    List<Category> getCategoryListByPage(String name, Integer offset, Integer pageSize);

    Long countCategoryList(String name);

    Integer countAllCategory();

    Category getCategoryInfo(String id);

    void updateCategory(String id, String name, Integer isDelete);

    List<Category> getAllCategoryList();

    List<CategoryFrontListItem> getAllCategoryFrontList();
}
