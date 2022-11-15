package com.sunil.blog.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.sunil.blog.entities.Post;
import com.sunil.blog.mapper.PostMapper;
import com.sunil.blog.payloads.CategoryDto;
import com.sunil.blog.payloads.PostDto;
import com.sunil.blog.payloads.UserDto;
import com.sunil.blog.repositories.PostRepo;
import com.sunil.blog.service.CategoryService;
import com.sunil.blog.service.PostService;
import com.sunil.blog.service.UserService;

@Service
public class PostServiceImpl implements PostService {

    @Autowired
    private PostRepo postRepo;

    @Autowired
    private PostMapper postMapper;

    @Autowired
    private UserService userService;

    @Autowired
    private CategoryService categoryService;

    @Override
    public PostDto createPost(PostDto postDto, Integer categoryId, Integer userId) {
        UserDto userDto = this.userService.getUserById(userId);
        CategoryDto categoryDto = this.categoryService.getCategoryById(categoryId);

        postDto.setCategory(categoryDto);
        postDto.setUser(userDto);
        postDto.setAddedDate(new Date());
        postDto.setImage("default.png");

        Post post = this.postMapper.postDtoToPost(postDto);
        Post savedPost = this.postRepo.save(post);

        return this.postMapper.postToPostDto(savedPost);
    }

    @Override
    public PostDto updatePost(PostDto postDto, Integer id) {

        return null;
    }

    @Override
    public void deletePost(Integer id) {
        // TODO Auto-generated method stub

    }

    @Override
    public List<PostDto> getAllPost() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public PostDto getPostById(Integer id) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<PostDto> getPostByCategory(Integer categoryId) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<PostDto> getPostByUser(Integer userId) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<PostDto> searchPost(String keyword) {
        // TODO Auto-generated method stub
        return null;
    }

}
