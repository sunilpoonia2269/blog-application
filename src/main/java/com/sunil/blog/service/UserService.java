package com.sunil.blog.service;

import java.util.List;

import com.sunil.blog.payloads.UserDto;

public interface UserService {
    UserDto createUser(UserDto userDto);

    UserDto registerUser(UserDto userDto);

    UserDto updateUser(UserDto userDto, Integer id);

    UserDto getUserById(Integer id);

    UserDto getUserbyEmail(String email);

    List<UserDto> getAllUsers();

    void deleteUser(Integer id);

}
