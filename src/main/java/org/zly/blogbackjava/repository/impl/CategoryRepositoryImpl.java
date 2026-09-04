package org.zly.blogbackjava.repository.impl;

import org.springframework.stereotype.Component;
import org.zly.blogbackjava.entity.Category;
import org.zly.blogbackjava.mapper.CategoryMapper;
import org.zly.blogbackjava.repository.CategoryRepository;

import java.util.List;

@Component
public class CategoryRepositoryImpl implements CategoryRepository {

    private final CategoryMapper categoryMapper;

    public CategoryRepositoryImpl(CategoryMapper categoryMapper) {
        this.categoryMapper = categoryMapper;
    }

    @Override
    public void insertCategory(String id, String name) {
        categoryMapper.insertCategory(id, name);
    }

    @Override
    public List<Category> getCategoryListByPage(String name, Integer offset, Integer pageSize) {
        return categoryMapper.getCategoryListByPage(name, offset, pageSize);
    }

    @Override
    public Long countCategoryList(String name) {
        return categoryMapper.countCategoryList(name);
    }
}
