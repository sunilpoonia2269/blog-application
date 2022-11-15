package com.sunil.blog.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import com.sunil.blog.entities.Category;
import com.sunil.blog.payloads.CategoryDto;

public class CategoryMapper {

    @Autowired
    private ModelMapper modelMapper;

    public Category categoryDtoToCategory(CategoryDto categoryDto) {
        return this.modelMapper.map(categoryDto, Category.class);
    }

    public CategoryDto categoryTocategoryDto(Category category) {
        return this.modelMapper.map(category, CategoryDto.class);
    }

}
