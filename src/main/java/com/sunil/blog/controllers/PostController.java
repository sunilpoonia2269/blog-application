package com.sunil.blog.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sunil.blog.payloads.PostDto;
import com.sunil.blog.service.PostService;

@RestController
@RequestMapping("/post")
public class PostController {

    @Autowired
    private PostService postService;

    @PostMapping("/create/category/{categoryId}/user/{userId}")
    public ResponseEntity<PostDto> createPost(@RequestBody PostDto postDto,
            @PathVariable Integer categoryId, @PathVariable Integer userId) {
        PostDto createdPostDto = this.postService.createPost(postDto, categoryId, userId);

        return new ResponseEntity<>(createdPostDto, HttpStatus.CREATED);
    }

}
