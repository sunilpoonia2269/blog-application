package com.sunil.blog.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import com.sunil.blog.entities.Post;
import com.sunil.blog.payloads.PostDto;

public class PostMapper {

    @Autowired
    private ModelMapper modelMapper;

    public Post postDtoToPost(PostDto postDto) {
        return this.modelMapper.map(postDto, Post.class);
    }

    public PostDto postToPostDto(Post post) {
        return this.modelMapper.map(post, PostDto.class);
    }

}
