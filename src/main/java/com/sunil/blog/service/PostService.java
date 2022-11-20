package com.sunil.blog.service;

import com.sunil.blog.payloads.PostDto;
import com.sunil.blog.payloads.PostResponse;

public interface PostService {

    PostDto createPost(PostDto postDto, Integer categoryId, Integer userId);

    PostDto updatePost(PostDto postDto, Integer id);

    void deletePost(Integer id);

    PostResponse getAllPost(Integer pageSize, Integer pageNumber, String sortBy, String dir);

    PostDto getPostById(Integer id);

    PostResponse getPostByCategory(Integer categoryId, Integer pageSize, Integer pageNumber, String sortBy,
            String dir);

    PostResponse getPostByUser(Integer userId, Integer pageSize, Integer pageNumber, String sortBy, String dir);

    PostResponse searchPost(String keyword, Integer pageSize, Integer pageNumber, String sortBy, String dir);

}
