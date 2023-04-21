package com.sparaochpara.sop.service;

import com.sparaochpara.sop.dto.CategoryDto;

import java.util.List;

public interface CategoryService {
    List<CategoryDto> findAllCategories();
}
