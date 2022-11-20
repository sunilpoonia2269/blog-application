package com.sunil.blog.service;

import com.sunil.blog.payloads.CommentDto;

public interface CommentService {
    CommentDto createComment(CommentDto commentDto, Integer userId, Integer postId);

    void deleteComment(Integer commentId);
}
