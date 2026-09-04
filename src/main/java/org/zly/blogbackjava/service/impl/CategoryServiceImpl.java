package org.zly.blogbackjava.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.zly.blogbackjava.dto.response.CategoryPageListItem;
import org.zly.blogbackjava.dto.response.CategoryPageListResult;
import org.zly.blogbackjava.exception.BusinessException;
import org.zly.blogbackjava.repository.CategoryRepository;
import org.zly.blogbackjava.service.CategoryService;
import org.zly.blogbackjava.util.IdGenerator;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public void createCategory(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new BusinessException(400, "分类名称不能为空");
        }
        categoryRepository.insertCategory(IdGenerator.generateId(), name);
    }

    @Override
    @Transactional
    public CategoryPageListResult findByPage(String name, Integer pageNum, Integer pageSize) {
        Integer offset = (pageNum - 1) * pageSize;
        List<CategoryPageListItem> list = categoryRepository.getCategoryListByPage(name, offset, pageSize)
                .stream().map(category -> new CategoryPageListItem(category.getId(), category.getName(), category.getCreateTime(), category.getUpdateTime())).toList();
        Long count = categoryRepository.countCategoryList(name);
        return new CategoryPageListResult(list, pageNum, pageSize, count);
    }
}
