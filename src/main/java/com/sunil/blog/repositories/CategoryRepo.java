package com.sunil.blog.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sunil.blog.entities.Category;

public interface CategoryRepo extends JpaRepository<Category, Integer> {

}
