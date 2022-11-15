package com.sunil.blog.service;

import java.util.List;

import com.sunil.blog.payloads.CategoryDto;
import com.sunil.blog.payloads.PostDto;
import com.sunil.blog.payloads.UserDto;

public interface PostService {

    PostDto createPost(PostDto postDto);

    PostDto updatePost(PostDto postDto, Integer id);

    void deletePost(Integer id);

    List<PostDto> getAllPost();

    PostDto getPostById(Integer id);

    List<PostDto> getPostByCategory(CategoryDto category);

    List<PostDto> getPostByUser(UserDto user);

}
