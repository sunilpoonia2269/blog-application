package com.sunil.blog.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.sunil.blog.mapper.CategoryMapper;
import com.sunil.blog.mapper.PostMapper;
import com.sunil.blog.mapper.UserMapper;

@Configuration
public class BeanConfig {

    @Bean
    public ModelMapper getModelMapper() {
        return new ModelMapper();
    }

    @Bean
    public UserMapper getUserMapper() {
        return new UserMapper();
    }

    @Bean
    public CategoryMapper getCategoryMapper() {
        return new CategoryMapper();
    }

    @Bean
    public PostMapper getPostMapper() {
        return new PostMapper();
    }
}
