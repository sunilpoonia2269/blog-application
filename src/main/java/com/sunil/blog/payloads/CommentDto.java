package com.sunil.blog.payloads;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentDto {

    private Integer id;

    @NotEmpty(message = "Comment content is required")
    @Size(min = 5, max = 200, message = "Comment content is not valid")
    private String content;

    private UserDto user;
}
