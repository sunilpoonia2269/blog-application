package com.sunil.blog.service;

import java.util.List;

import com.sunil.blog.payloads.CategoryDto;

public interface CategoryService {

    CategoryDto createCategory(CategoryDto categoryDto);

    CategoryDto updateCategory(CategoryDto categoryDto, Integer categoryId);

    void deleteCategory(Integer categoryId);

    CategoryDto getCategoryById(Integer categoryId);

    List<CategoryDto> getAllCategory();

}
