package com.sunil.blog.service;

import com.sunil.blog.entities.User;
import com.sunil.blog.payloads.CommentDto;

public interface CommentService {
    CommentDto createComment(CommentDto commentDto, Integer userId, Integer postId);

    void deleteComment(Integer commentId);

    void deleteCommentByUser(Integer commentId, User user);
}
