package com.sunil.blog.service;

import java.util.List;

import com.sunil.blog.payloads.PostDto;

public interface PostService {

    PostDto createPost(PostDto postDto, Integer categoryId, Integer userId);

    PostDto updatePost(PostDto postDto, Integer id);

    void deletePost(Integer id);

    List<PostDto> getAllPost(Integer pageSize, Integer pageNumber, String sortBy);

    PostDto getPostById(Integer id);

    List<PostDto> getPostByCategory(Integer categoryId);

    List<PostDto> getPostByUser(Integer userId);

    List<PostDto> searchPost(String keyword);

}
