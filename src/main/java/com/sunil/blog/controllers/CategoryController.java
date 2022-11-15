package com.sunil.blog.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sunil.blog.payloads.ApiResponse;
import com.sunil.blog.payloads.CategoryDto;
import com.sunil.blog.service.CategoryService;

@RestController
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @PostMapping("/create")
    public ResponseEntity<CategoryDto> createCategory(@Valid @RequestBody CategoryDto category) {
        CategoryDto categoryDto = this.categoryService.createCategory(category);
        return new ResponseEntity<>(categoryDto, HttpStatus.CREATED);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<CategoryDto> updateCategory(@Valid @RequestBody CategoryDto category,
            @PathVariable Integer id) {
        CategoryDto categoryDto = this.categoryService.updateCategory(category, id);
        return new ResponseEntity<>(categoryDto, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResponse> deleteCategory(@PathVariable Integer id) {
        this.categoryService.deleteCategory(id);
        ApiResponse apiResponse = new ApiResponse("Category Deleted Successfully", true);
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<CategoryDto> getCategoryById(@PathVariable Integer id) {
        CategoryDto categoryDto = this.categoryService.getCategoryById(id);
        return new ResponseEntity<>(categoryDto, HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<List<CategoryDto>> getAllCategory() {
        List<CategoryDto> categoryDtos = this.categoryService.getAllCategory();
        return new ResponseEntity<>(categoryDtos, HttpStatus.CREATED);
    }

}
