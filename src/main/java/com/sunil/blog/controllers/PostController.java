package com.sunil.blog.controllers;

import java.util.List;

import javax.validation.Valid;
import javax.websocket.server.PathParam;
import javax.xml.ws.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sunil.blog.payloads.ApiResponse;
import com.sunil.blog.payloads.PostDto;
import com.sunil.blog.service.PostService;

@RestController
@RequestMapping("/post")
public class PostController {

    @Autowired
    private PostService postService;

    @PostMapping("/create/category/{categoryId}/user/{userId}")
    public ResponseEntity<PostDto> createPost(@Valid @RequestBody PostDto postDto,
            @PathVariable Integer categoryId, @PathVariable Integer userId) {
        PostDto createdPostDto = this.postService.createPost(postDto, categoryId, userId);

        return new ResponseEntity<>(createdPostDto, HttpStatus.CREATED);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<PostDto> udpatePost(@Valid @RequestBody PostDto postDto, @PathVariable Integer id) {
        PostDto updatedPostDto = this.postService.updatePost(postDto, id);
        return new ResponseEntity<>(updatedPostDto, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResponse> deletePost(@PathVariable Integer id) {
        this.postService.deletePost(id);
        ApiResponse apiResponse = new ApiResponse("Post Deleted successfully", true);
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<PostDto> getPostById(@PathVariable Integer id) {
        PostDto postDto = this.postService.getPostById(id);
        return new ResponseEntity<>(postDto, HttpStatus.OK);
    }

    @GetMapping("/get/all")
    public ResponseEntity<List<PostDto>> getAllPost(
            @RequestParam(defaultValue = "5") Integer pageSize,
            @RequestParam(defaultValue = "0") Integer pageNumber,
            @RequestParam(required = false) String sortBy) {

        List<PostDto> postDtos = this.postService.getAllPost(pageSize, pageNumber, sortBy);
        return new ResponseEntity<>(postDtos, HttpStatus.OK);
    }

    @GetMapping("/get/user/{userId}")
    public ResponseEntity<List<PostDto>> getPostByUser(@PathVariable Integer userId) {
        List<PostDto> postDtos = this.postService.getPostByUser(userId);
        return new ResponseEntity<>(postDtos, HttpStatus.OK);
    }

    @GetMapping("/get/category/{categoryId}")
    public ResponseEntity<List<PostDto>> getPostByCategory(@PathVariable Integer categoryId) {
        List<PostDto> postDtos = this.postService.getPostByCategory(categoryId);
        return new ResponseEntity<>(postDtos, HttpStatus.OK);
    }

}
