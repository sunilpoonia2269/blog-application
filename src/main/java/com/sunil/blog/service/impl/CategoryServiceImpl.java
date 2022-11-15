package com.sunil.blog.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sunil.blog.entities.Category;
import com.sunil.blog.exceptions.ResourceNotFoundException;
import com.sunil.blog.payloads.CategoryDto;
import com.sunil.blog.repositories.CategoryRepo;
import com.sunil.blog.service.CategoryService;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepo categoryRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public CategoryDto createCategory(CategoryDto categoryDto) {
        Category category = this.categoryDtoToCategory(categoryDto);
        Category savedCategory = this.categoryRepo.save(category);
        return this.categoryTocategoryDto(savedCategory);
    }

    @Override
    public CategoryDto updateCategory(CategoryDto categoryDto, Integer categoryId) {
        Category category = this.categoryRepo.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "Id", "" + categoryId));

        category.setCategoryTitle(categoryDto.getCategoryTitle());
        category.setCategoryDescription(categoryDto.getCategoryDescription());

        Category updatedCategory = this.categoryRepo.save(category);
        return this.categoryTocategoryDto(updatedCategory);
    }

    @Override
    public void deleteCategory(Integer categoryId) {
        Category category = this.categoryRepo.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "Id", "" + categoryId));

        this.categoryRepo.delete(category);
    }

    @Override
    public CategoryDto getCategoryById(Integer categoryId) {
        Category category = this.categoryRepo.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "Id", "" + categoryId));
        return this.categoryTocategoryDto(category);
    }

    @Override
    public List<CategoryDto> getAllCategory() {
        List<Category> categories = this.categoryRepo.findAll();

        List<CategoryDto> categoryDtos = categories.stream().map((category) -> this.categoryTocategoryDto(category))
                .collect(Collectors.toList());
        return categoryDtos;
    }

    ///
    ///
    // Private method used to map the classes in one other
    private Category categoryDtoToCategory(CategoryDto categoryDto) {
        Category category = this.modelMapper.map(categoryDto, Category.class);
        return category;
    }

    private CategoryDto categoryTocategoryDto(Category category) {
        CategoryDto categoryDto = this.modelMapper.map(category, CategoryDto.class);
        return categoryDto;
    }

}
