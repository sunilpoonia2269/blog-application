package com.sunil.blog.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sunil.blog.entities.Category;
import com.sunil.blog.entities.Post;
import com.sunil.blog.entities.User;

public interface PostRepo extends JpaRepository<Post, Integer> {
    List<Post> findByUser(User user);

    List<Post> findByCategory(Category category);

}
