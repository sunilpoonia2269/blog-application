package com.sunil.blog.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import com.sunil.blog.entities.Category;
import com.sunil.blog.entities.Post;
import com.sunil.blog.entities.User;

public interface PostRepo extends JpaRepository<Post, Integer> {
    Page<Post> findByUser(User user, Pageable pageable);

    Page<Post> findByCategory(Category category, Pageable pageable);

    // @Query("select p from Post p where p.title like %:key%")
    Page<Post> findByTitleContaining(@Param("key") String keyword, Pageable pageable);

}
