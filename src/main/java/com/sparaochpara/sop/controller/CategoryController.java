package com.sparaochpara.sop.controller;

import com.sparaochpara.sop.dto.CategoryDto;
import com.sparaochpara.sop.model.Category;
import com.sparaochpara.sop.service.CategoryService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/categories")
public class CategoryController {
    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping("/names")
    public List<String> getCategoryNames() {
        List<CategoryDto> categories = categoryService.findAllCategories();
        List<String> categoryNames = categories.stream()
                .map(CategoryDto::getName)
                .collect(Collectors.toList());

        for(String dto : categoryNames){
            System.out.println(dto);
        }
        return categoryNames;
    }
}

