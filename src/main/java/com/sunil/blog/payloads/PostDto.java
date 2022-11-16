package com.sunil.blog.payloads;

import java.util.Date;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class PostDto {
    private Integer id;

    @NotEmpty(message = "Title is required")
    @Size(min = 3, message = "Title is too short")
    private String title;

    @NotEmpty(message = "Content is required")
    @Size(min = 10, max = 1000, message = "Content should be between 10 to 1000 char")
    private String content;

    private String image;
    private Date addedDate;
    private CategoryDto category;
    private UserDto user;
}
