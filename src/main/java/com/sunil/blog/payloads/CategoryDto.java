package com.sunil.blog.payloads;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class CategoryDto {
    private Integer categoryId;

    @NotEmpty(message = "Category title is required")
    @Size(min = 2, message = "Category title should be atleast 2 chararcters")
    private String categoryTitle;
    private String categoryDescription;
}
