package com.sunil.blog.controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sunil.blog.entities.User;
import com.sunil.blog.mapper.UserMapper;
import com.sunil.blog.payloads.JwtAuthRequest;
import com.sunil.blog.payloads.JwtAuthResponse;
import com.sunil.blog.payloads.UserDto;
import com.sunil.blog.security.JwtTokenHelper;
import com.sunil.blog.service.UserService;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private JwtTokenHelper jwtTokenHelper;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserService userService;

    @Autowired
    private UserMapper userMapper;

    @PostMapping("/login")
    public ResponseEntity<JwtAuthResponse> loginHandler(@Valid @RequestBody JwtAuthRequest authRequest) {
        this.authenticate(authRequest);
        UserDetails userDetails = this.userDetailsService.loadUserByUsername(authRequest.getEmail());

        String token = this.jwtTokenHelper.generateToken(userDetails);

        UserDto userDto = this.userMapper.userToUserDto((User) userDetails);
        JwtAuthResponse response = this.createResponse(userDto, token);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/register")
    public ResponseEntity<JwtAuthResponse> registerUser(@Valid @RequestBody UserDto userDto) {
        UserDto registerdUser = this.userService.registerUser(userDto);
        System.out.println("Hello there");
        System.out.println(registerdUser.getRoles());

        User user = this.userMapper.userDtoToUser(registerdUser);
        String token = this.jwtTokenHelper.generateToken(user);

        JwtAuthResponse response = this.createResponse(registerdUser, token);

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    private void authenticate(JwtAuthRequest authRequest) {
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                authRequest.getEmail(), authRequest.getPassword());
        this.authenticationManager.authenticate(usernamePasswordAuthenticationToken);
    }

    private JwtAuthResponse createResponse(UserDto userDto, String token) {
        JwtAuthResponse jwtAuthResponse = new JwtAuthResponse();
        jwtAuthResponse.setUserDto(userDto);
        jwtAuthResponse.setToken(token);
        return jwtAuthResponse;
    }

}
