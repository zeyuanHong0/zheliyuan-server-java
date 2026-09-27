package org.zly.blogbackjava.mapper;

import org.apache.ibatis.annotations.Param;
import org.zly.blogbackjava.dto.category.response.CategoryFrontInfo;
import org.zly.blogbackjava.dto.category.response.CategoryFrontListItem;
import org.zly.blogbackjava.entity.Category;

import java.util.List;

public interface CategoryMapper {

    void insertCategory(@Param("id") String id, @Param("name") String name);

    List<Category> getCategoryListByPage(@Param("name") String name, @Param("offset") Integer offset, @Param("pageSize") Integer pageSize);

    Long countCategoryList(@Param("name") String name);

    Integer countAllCategory();

    Category getCategoryById(@Param("id") String id);

    void updateCategory(@Param("id") String id, @Param("name") String name, @Param("isDelete") Integer isDelete);

    List<Category> getAllCategoryList();

    List<CategoryFrontListItem> getAllCategoryFrontList();

    CategoryFrontInfo getCategoryFrontInfo(@Param("categoryId") String categoryId);
}
