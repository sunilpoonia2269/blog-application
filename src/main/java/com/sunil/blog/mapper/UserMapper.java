package com.sunil.blog.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import com.sunil.blog.entities.User;
import com.sunil.blog.payloads.UserDto;

public class UserMapper {

    @Autowired
    private ModelMapper modelMapper;

    public User userDtoToUser(UserDto userDto) {
        return this.modelMapper.map(userDto, User.class);
    }

    public UserDto userToUserDto(User user) {
        return this.modelMapper.map(user, UserDto.class);
    }

}
