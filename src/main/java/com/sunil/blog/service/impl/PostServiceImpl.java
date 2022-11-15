package com.sunil.blog.service.impl;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sunil.blog.payloads.CategoryDto;
import com.sunil.blog.payloads.PostDto;
import com.sunil.blog.payloads.UserDto;
import com.sunil.blog.repositories.PostRepo;
import com.sunil.blog.service.PostService;

@Service
public class PostServiceImpl implements PostService {

    @Autowired
    private PostRepo postRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public PostDto createPost(PostDto postDto) {

        return null;
    }

    @Override
    public PostDto updatePost(PostDto postDto, Integer id) {
        // TODO Auto-generated method stub
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
    public List<PostDto> getPostByCategory(CategoryDto category) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<PostDto> getPostByUser(UserDto user) {
        // TODO Auto-generated method stub
        return null;
    }

}
