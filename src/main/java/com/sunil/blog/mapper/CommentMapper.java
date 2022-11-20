package com.sunil.blog.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import com.sunil.blog.entities.Comment;
import com.sunil.blog.payloads.CommentDto;

public class CommentMapper {

    @Autowired
    private ModelMapper modelMapper;

    public Comment commentDtoToComment(CommentDto commentDto) {
        return this.modelMapper.map(commentDto, Comment.class);
    }

    public CommentDto commentToCommentDto(Comment comment) {
        return this.modelMapper.map(comment, CommentDto.class);
    }
}
