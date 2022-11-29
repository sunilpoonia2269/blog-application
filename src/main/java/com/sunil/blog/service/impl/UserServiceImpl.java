package com.sunil.blog.service.impl;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.sunil.blog.config.AppConstants;
import com.sunil.blog.entities.Role;
import com.sunil.blog.entities.User;
import com.sunil.blog.exceptions.ResourceNotFoundException;
import com.sunil.blog.mapper.UserMapper;
import com.sunil.blog.payloads.UserDto;
import com.sunil.blog.repositories.RoleRepo;
import com.sunil.blog.repositories.UserRepo;
import com.sunil.blog.service.UserService;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private RoleRepo roleRepo;

    @Override
    public UserDto createUser(UserDto userDto) {
        userDto.setPassword(this.passwordEncoder.encode(userDto.getPassword()));
        User user = userMapper.userDtoToUser(userDto);

        Role userRole = this.roleRepo.findById(AppConstants.USER_ROLE_ID)
                .orElseThrow(() -> new ResourceNotFoundException("Role", "Id", "" + AppConstants.USER_ROLE_ID));

        user.getRoles().add(userRole);
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

    @Override
    public UserDto getUserbyEmail(String email) {
        User user = this.userRepo.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User", "email", email));
        return this.userMapper.userToUserDto(user);
    }

    @Override
    public UserDto registerUser(UserDto userDto) {

        userDto.setPassword(this.passwordEncoder.encode(userDto.getPassword()));

        /// Adding roles
        Role userRole = this.roleRepo.findById(AppConstants.USER_ROLE_ID)
                .orElseThrow(() -> new ResourceNotFoundException("Role", "Id", "" + AppConstants.USER_ROLE_ID));
        User user = this.userMapper.userDtoToUser(userDto);

        user.getRoles().add(userRole);

        User newUser = this.userRepo.save(user);

        return this.userMapper.userToUserDto(newUser);
    }

}
