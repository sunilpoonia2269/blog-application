package com.sunil.blog.payloads;

import java.util.Date;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class PostDto {
    private Integer id;
    private String title;
    private String content;
    private Date addedDate;
    private CategoryDto category;
    private UserDto user;
}
