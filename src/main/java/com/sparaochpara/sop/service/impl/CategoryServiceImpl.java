package com.sparaochpara.sop.service.impl;

import com.sparaochpara.sop.dto.CategoryDto;
import com.sparaochpara.sop.model.Category;
import com.sparaochpara.sop.repository.CategoryRepository;
import com.sparaochpara.sop.service.CategoryService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
@Service
public class CategoryServiceImpl implements CategoryService {
    private CategoryRepository categoryRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public List<CategoryDto> findAllCategories() {
        List<Category> categories = categoryRepository.findAll();
        return categories.stream().map((category) -> mapToCategoryDto(category)).collect(Collectors.toList());
    }

    @Override
    public CategoryDto findCategoryById(Long id) {
            Category category = categoryRepository.findById(id).get();
            CategoryDto categoryDto = mapToCategoryDto(category);
        return categoryDto;
    }

    private CategoryDto mapToCategoryDto(Category category) {
        CategoryDto categoryDto = CategoryDto.builder()
                .id(category.getId())
                .name(category.getName())
                .build();
        return categoryDto;
    }
}
