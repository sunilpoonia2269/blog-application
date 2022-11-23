package com.sunil.blog.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import com.sunil.blog.entities.Comment;
import com.sunil.blog.entities.Post;
import com.sunil.blog.entities.User;
import com.sunil.blog.exceptions.ResourceNotFoundException;
import com.sunil.blog.mapper.CommentMapper;
import com.sunil.blog.mapper.PostMapper;
import com.sunil.blog.payloads.CommentDto;
import com.sunil.blog.payloads.PostDto;
import com.sunil.blog.payloads.UserDto;
import com.sunil.blog.repositories.CommentRepo;
import com.sunil.blog.service.CommentService;
import com.sunil.blog.service.PostService;
import com.sunil.blog.service.UserService;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentRepo commentRepo;

    @Autowired
    private UserService userService;

    @Autowired
    private PostService postService;

    @Autowired
    private PostMapper postMapper;

    @Autowired
    private CommentMapper commentMapper;

    @Override
    public CommentDto createComment(CommentDto commentDto, Integer userId, Integer postId) {
        UserDto userDto = this.userService.getUserById(userId);
        commentDto.setUser(userDto);
        PostDto postDto = this.postService.getPostById(postId);

        Post post = this.postMapper.postDtoToPost(postDto);

        Comment comment = this.commentMapper.commentDtoToComment(commentDto);
        comment.setPost(post);

        Comment savedComment = this.commentRepo.save(comment);
        return this.commentMapper.commentToCommentDto(savedComment);
    }

    @Override
    public void deleteComment(Integer commentId) {
        Comment comment = this.commentRepo.findById(commentId)
                .orElseThrow(() -> new ResourceNotFoundException("Comment",
                        "id", "" + commentId));
        this.commentRepo.delete(comment);

    }

    @Override
    public void deleteCommentByUser(Integer commentId, User user) {
        Comment comment = this.commentRepo.findById(commentId)
                .orElseThrow(() -> new ResourceNotFoundException("Comment",
                        "id", "" + commentId));

        if (comment.getUser().getId() != user.getId())
            throw new AccessDeniedException("Access Denied for Delete Comment");
        this.commentRepo.delete(comment);
    }

}
