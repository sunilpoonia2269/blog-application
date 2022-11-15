package com.sunil.blog.service.impl;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sunil.blog.entities.User;
import com.sunil.blog.exceptions.ResourceNotFoundException;
import com.sunil.blog.mapper.UserMapper;
import com.sunil.blog.payloads.UserDto;
import com.sunil.blog.repositories.UserRepo;
import com.sunil.blog.service.UserService;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private UserMapper userMapper;

    @Override
    public UserDto createUser(UserDto userDto) {
        User user = userMapper.userDtoToUser(userDto);
        User savedUser = this.userRepo.save(user);

        return userMapper.userToUserDto(savedUser);
    }

    @Override
    public UserDto updateUser(UserDto userDto, Integer id) {

        User user = this.userRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User", "Id", "" + id));

        user.setName(userDto.getName());
        user.setEmail(userDto.getEmail());
        user.setPassword(userDto.getPassword());
        user.setAbout(userDto.getAbout());

        User udpatedUser = this.userRepo.save(user);

        return userMapper.userToUserDto(udpatedUser);
    }

    @Override
    public UserDto getUserById(Integer id) {
        User user = this.userRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User", "Id", "" + id));

        return userMapper.userToUserDto(user);
    }

    @Override
    public List<UserDto> getAllUsers() {

        List<User> userList = this.userRepo.findAll();

        List<UserDto> userDtoList = userList.stream().map((user) -> userMapper.userToUserDto(user))
                .collect(Collectors.toList());

        return userDtoList;
    }

    @Override
    public void deleteUser(Integer id) {
        User user = this.userRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User", "Id", "" + id));
        this.userRepo.delete(user);
    }

}
