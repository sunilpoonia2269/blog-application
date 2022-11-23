package com.sunil.blog.controllers;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sunil.blog.entities.User;
import com.sunil.blog.payloads.ApiResponse;
import com.sunil.blog.payloads.CommentDto;
import com.sunil.blog.service.CommentService;
import com.sunil.blog.utils.RequestHelper;

@RestController
@RequestMapping("/comment")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @Autowired
    private RequestHelper requestHelper;

    @PostMapping("/create/post/{postId}/user/{userId}")
    public ResponseEntity<CommentDto> createComment(@RequestBody CommentDto commentDto, @PathVariable Integer postId,
            @PathVariable Integer userId) {

        CommentDto savedCommentDto = this.commentService.createComment(commentDto, userId, postId);
        return new ResponseEntity<>(savedCommentDto, HttpStatus.CREATED);
    }

    @DeleteMapping("/delete/{commentId}")
    public ResponseEntity<ApiResponse> deleteComment(@PathVariable Integer commentId, HttpServletRequest request) {

        User currentUser = this.requestHelper.getUserFromRequest(request);
        this.commentService.deleteCommentByUser(commentId, currentUser);
        ApiResponse apiResponse = new ApiResponse("Comment Deleted Successfully", true);
        return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/admin/delete/{commentId}")
    public ResponseEntity<ApiResponse> deleteCommentByAdmin(@PathVariable Integer commentId) {
        this.commentService.deleteComment(commentId);
        ApiResponse apiResponse = new ApiResponse("Comment Deleted Successfully", true);
        return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.OK);
    }

}
