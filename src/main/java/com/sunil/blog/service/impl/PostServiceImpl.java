package com.sunil.blog.service.impl;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.security.access.AccessDeniedException;
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
import com.sunil.blog.payloads.PostResponse;
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
        post.setImage(postDto.getImage());

        Post updatedPost = this.postRepo.save(post);

        return this.postMapper.postToPostDto(updatedPost);
    }

    @Override
    public void deletePost(Integer id) {
        Post post = this.postRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post", "Id", "" + id));

        this.postRepo.delete(post);
    }

    @Override
    public PostResponse getAllPost(Integer pageSize, Integer pageNumber, String sortBy, String dir) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by(Direction.fromString(dir), sortBy));
        Page<Post> postPage = this.postRepo.findAll(pageable);
        List<Post> posts = postPage.getContent();

        List<PostDto> postDtos = posts.stream().map((post) -> this.postMapper.postToPostDto(post))
                .collect(Collectors.toList());

        return this.createPostResponse(postDtos, postPage);
    }

    @Override
    public PostDto getPostById(Integer id) {
        Post post = this.postRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post", "Id", "" + id));

        return this.postMapper.postToPostDto(post);
    }

    @Override
    public PostResponse getPostByCategory(Integer categoryId, Integer pageSize, Integer pageNumber, String sortBy,
            String dir) {
        CategoryDto categoryDto = this.categoryService.getCategoryById(categoryId);
        Category category = this.categoryMapper.categoryDtoToCategory(categoryDto);

        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by(Direction.fromString(dir), sortBy));
        Page<Post> postPage = this.postRepo.findByCategory(category, pageable);

        List<Post> posts = postPage.getContent();
        List<PostDto> postDtos = posts.stream().map((post) -> this.postMapper.postToPostDto(post))
                .collect(Collectors.toList());

        return this.createPostResponse(postDtos, postPage);
    }

    @Override
    public PostResponse getPostByUser(Integer userId, Integer pageSize, Integer pageNumber, String sortBy,
            String dir) {
        UserDto userDto = this.userService.getUserById(userId);
        User user = this.userMapper.userDtoToUser(userDto);

        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by(Direction.fromString(dir), sortBy));
        Page<Post> postPage = this.postRepo.findByUser(user, pageable);
        List<Post> posts = postPage.getContent();
        List<PostDto> postDtos = posts.stream().map((post) -> this.postMapper.postToPostDto(post))
                .collect(Collectors.toList());

        return this.createPostResponse(postDtos, postPage);
    }

    @Override
    public PostResponse searchPost(String keyword, Integer pageSize, Integer pageNumber, String sortBy, String dir) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by(Direction.fromString(dir), sortBy));
        Page<Post> postPage = this.postRepo.findByTitleContaining(keyword, pageable);
        List<Post> posts = postPage.getContent();
        List<PostDto> postDtos = posts.stream().map((post) -> this.postMapper.postToPostDto(post))
                .collect(Collectors.toList());
        return this.createPostResponse(postDtos, postPage);
    }

    private PostResponse createPostResponse(
            List<PostDto> postDtos,
            Page<Post> postPage) {
        PostResponse postResponse = new PostResponse();
        postResponse.setContent(postDtos);
        postResponse.setPageNumber(postPage.getNumber());
        postResponse.setPageSize(postPage.getSize());
        postResponse.setTotalElements(postPage.getTotalElements());
        postResponse.setTotalPages(postPage.getTotalPages());
        postResponse.setIsLastPage(postPage.isLast());
        return postResponse;
    }

    @Override
    public void deletePostByUser(Integer id, User user) {
        Post post = this.postRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post", "Id", "" + id));

        if (post.getUser().getId() != user.getId())
            throw new AccessDeniedException("Access is denied for delete Post");

        this.postRepo.delete(post);

    }

}
