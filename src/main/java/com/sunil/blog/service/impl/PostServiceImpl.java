package com.sunil.blog.service.impl;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.sunil.blog.entities.Category;
import com.sunil.blog.entities.Post;
import com.sunil.blog.entities.User;
import com.sunil.blog.exceptions.ResourceNotFoundException;
import com.sunil.blog.mapper.CategoryMapper;
import com.sunil.blog.mapper.PostMapper;
import com.sunil.blog.mapper.UserMapper;
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

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private CategoryMapper categoryMapper;

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
        Post post = this.postRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post", "Id", "" + id));

        post.setTitle(postDto.getTitle());
        post.setContent(postDto.getContent());

        Post updatedPost = this.postRepo.save(post);

        return this.postMapper.postToPostDto(updatedPost);
    }

    @Override
    public void deletePost(Integer id) {
        Post post = this.postRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post", "Id", "" + id));

        this.postRepo.delete(post);
    }

    @Override
    public List<PostDto> getAllPost(Integer pageSize, Integer pageNumber, String sortBy) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Page<Post> postPage = this.postRepo.findAll(pageable);
        List<Post> posts = postPage.getContent();
        List<PostDto> postDtos = posts.stream().map((post) -> this.postMapper.postToPostDto(post))
                .collect(Collectors.toList());
        return postDtos;
    }

    @Override
    public PostDto getPostById(Integer id) {
        Post post = this.postRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post", "Id", "" + id));

        return this.postMapper.postToPostDto(post);
    }

    @Override
    public List<PostDto> getPostByCategory(Integer categoryId) {
        CategoryDto categoryDto = this.categoryService.getCategoryById(categoryId);

        Category category = this.categoryMapper.categoryDtoToCategory(categoryDto);

        List<Post> posts = this.postRepo.findByCategory(category);

        List<PostDto> postDtos = posts.stream().map((post) -> this.postMapper.postToPostDto(post))
                .collect(Collectors.toList());
        return postDtos;
    }

    @Override
    public List<PostDto> getPostByUser(Integer userId) {
        UserDto userDto = this.userService.getUserById(userId);
        User user = this.userMapper.userDtoToUser(userDto);

        List<Post> posts = this.postRepo.findByUser(user);
        List<PostDto> postDtos = posts.stream().map((post) -> this.postMapper.postToPostDto(post))
                .collect(Collectors.toList());

        return postDtos;
    }

    @Override
    public List<PostDto> searchPost(String keyword) {
        // TODO Auto-generated method stub
        return null;
    }

}
