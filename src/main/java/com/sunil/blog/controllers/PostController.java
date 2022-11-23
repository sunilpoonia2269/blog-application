package com.sunil.blog.controllers;

import java.io.IOException;
import java.io.InputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.hibernate.engine.jdbc.StreamUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.sunil.blog.config.AppConstants;
import com.sunil.blog.entities.User;
import com.sunil.blog.payloads.ApiResponse;
import com.sunil.blog.payloads.PostDto;
import com.sunil.blog.payloads.PostResponse;
import com.sunil.blog.service.FileService;
import com.sunil.blog.service.PostService;
import com.sunil.blog.utils.RequestHelper;

@RestController
@RequestMapping("/post")
public class PostController {

    @Autowired
    private PostService postService;

    @Autowired
    private FileService fileService;

    @Autowired
    private RequestHelper requestHelper;

    @Value("${project.image}")
    private String path;

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

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/admin/delete/{id}")
    public ResponseEntity<ApiResponse> deletePostByAdmin(@PathVariable Integer id) {
        this.postService.deletePost(id);
        ApiResponse apiResponse = new ApiResponse("Post Deleted successfully", true);
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResponse> deletePostByUser(@PathVariable Integer id, HttpServletRequest request) {

        User currentUser = this.requestHelper.getUserFromRequest(request);

        this.postService.deletePostByUser(id, currentUser);
        ApiResponse apiResponse = new ApiResponse("Post Deleted successfully", true);
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<PostDto> getPostById(@PathVariable Integer id) {
        PostDto postDto = this.postService.getPostById(id);
        return new ResponseEntity<>(postDto, HttpStatus.OK);
    }

    @GetMapping("/get/all")
    public ResponseEntity<PostResponse> getAllPost(
            @RequestParam(defaultValue = AppConstants.PAGE_SIZE) Integer pageSize,
            @RequestParam(defaultValue = AppConstants.PAGE_NUMBER) Integer pageNumber,
            @RequestParam(defaultValue = AppConstants.SORT_BY) String sortBy,
            @RequestParam(defaultValue = AppConstants.SORT_DIR) String dir) {

        PostResponse postResponse = this.postService.getAllPost(pageSize, pageNumber, sortBy, dir);
        return new ResponseEntity<>(postResponse, HttpStatus.OK);
    }

    @GetMapping("/get/user/{userId}")
    public ResponseEntity<PostResponse> getPostByUser(
            @PathVariable Integer userId,
            @RequestParam(defaultValue = AppConstants.PAGE_SIZE) Integer pageSize,
            @RequestParam(defaultValue = AppConstants.PAGE_NUMBER) Integer pageNumber,
            @RequestParam(defaultValue = AppConstants.SORT_BY) String sortBy,
            @RequestParam(defaultValue = AppConstants.SORT_DIR) String dir) {
        PostResponse postResponse = this.postService.getPostByUser(userId, pageSize, pageNumber, sortBy, dir);
        return new ResponseEntity<>(postResponse, HttpStatus.OK);
    }

    @GetMapping("/get/category/{categoryId}")
    public ResponseEntity<PostResponse> getPostByCategory(
            @PathVariable Integer categoryId,
            @RequestParam(defaultValue = AppConstants.PAGE_SIZE) Integer pageSize,
            @RequestParam(defaultValue = AppConstants.PAGE_NUMBER) Integer pageNumber,
            @RequestParam(defaultValue = AppConstants.SORT_BY) String sortBy,
            @RequestParam(defaultValue = AppConstants.SORT_DIR) String dir) {
        PostResponse postResponse = this.postService.getPostByCategory(categoryId, pageSize, pageNumber, sortBy, dir);
        return new ResponseEntity<>(postResponse, HttpStatus.OK);
    }

    @GetMapping("/search")
    public ResponseEntity<PostResponse> searchPost(
            @RequestParam String keyword,
            @RequestParam(defaultValue = AppConstants.PAGE_SIZE) Integer pageSize,
            @RequestParam(defaultValue = AppConstants.PAGE_NUMBER) Integer pageNumber,
            @RequestParam(defaultValue = AppConstants.SORT_BY) String sortBy,
            @RequestParam(defaultValue = AppConstants.SORT_DIR) String dir) {
        PostResponse postResponse = this.postService.searchPost(keyword, pageSize, pageNumber, sortBy, dir);
        return new ResponseEntity<>(postResponse, HttpStatus.OK);
    }

    @PostMapping("/update/{postId}/image")
    public ResponseEntity<PostDto> uploadPostImage(
            @RequestParam("image") MultipartFile image, @PathVariable Integer postId) throws IOException {
        PostDto postDto = this.postService.getPostById(postId);

        String savedImage = this.fileService.uploadImage(path, image);
        postDto.setImage(savedImage);
        PostDto savedPostDto = this.postService.updatePost(postDto, postId);
        return new ResponseEntity<>(savedPostDto, HttpStatus.OK);
    }

    @GetMapping(value = "/get/image/{imageName}", produces = MediaType.IMAGE_JPEG_VALUE)
    public void getPostImage(@PathVariable String imageName, HttpServletResponse response)
            throws IOException {
        InputStream inputStream = this.fileService.getImage(path, imageName);
        response.setContentType(MediaType.IMAGE_JPEG_VALUE);
        StreamUtils.copy(inputStream, response.getOutputStream());

    }

}
