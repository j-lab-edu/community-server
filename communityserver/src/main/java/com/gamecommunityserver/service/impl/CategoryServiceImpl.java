package com.gamecommunityserver.service.impl;

import com.gamecommunityserver.dto.CategoryDTO;
import com.gamecommunityserver.mapper.CategoryMapper;
import com.gamecommunityserver.service.CategoryService;
import org.springframework.stereotype.Service;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryMapper categoryMapper;

    public CategoryServiceImpl(CategoryMapper categoryMapper) {
        this.categoryMapper = categoryMapper;
    }
    @Override
    public void addCategoryName(CategoryDTO categoryDTO){
        categoryMapper.register(categoryDTO);
    }
    @Override
    public int categoryDuplicateCheck(String categoryName){
        return categoryMapper.categoryDuplicateCheck(categoryName);
    }
    @Override
    public boolean categoryNumberCheck(int categoryNumber){
        return categoryMapper.categoryNumberCheck(categoryNumber) == 1;
    }

    @Override
    public void deleteCategoryNumber(int categoryNumber){
        categoryMapper.categoryDelete(categoryNumber);
    }
}
