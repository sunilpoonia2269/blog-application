package com.sunil.blog.controllers;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.sunil.blog.entities.User;
import com.sunil.blog.payloads.ApiResponse;
import com.sunil.blog.payloads.UserDto;
import com.sunil.blog.service.UserService;
import com.sunil.blog.utils.RequestHelper;

@RestController
@CrossOrigin
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private RequestHelper requestHelper;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/admin/add")
    public ResponseEntity<UserDto> createUser(@Valid @RequestBody UserDto userDto) {
        UserDto createdUserDto = this.userService.createUser(userDto);
        return new ResponseEntity<>(createdUserDto, HttpStatus.CREATED);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<UserDto> updateUser(@Valid @RequestBody UserDto userDto, @PathVariable Integer id) {
        UserDto updatedUser = this.userService.updateUser(userDto, id);

        return new ResponseEntity<>(updatedUser, HttpStatus.OK);
    }

    /// User can only delete to himself
    ///
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResponse> deleteUser(@PathVariable Integer id, HttpServletRequest request) {
        User currentUser = this.requestHelper.getUserFromRequest(request);

        if (currentUser.getId() != id)
            throw new AccessDeniedException("Access Denied - Trying to delete other user");

        this.userService.deleteUser(id);

        ApiResponse apiResponse = new ApiResponse("User Deleted Successfully", true);
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    /// Only admin can delete any user
    ///
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/admin/delete/{id}")
    public ResponseEntity<ApiResponse> deleteUserByAdmin(@PathVariable Integer id) {
        this.userService.deleteUser(id);

        ApiResponse apiResponse = new ApiResponse("User Deleted Successfully", true);
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<List<UserDto>> getAllUsers() {
        List<UserDto> userList = this.userService.getAllUsers();
        return new ResponseEntity<>(userList, HttpStatus.OK);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<UserDto> getUserById(@PathVariable Integer id) {
        UserDto userDto = this.userService.getUserById(id);

        return new ResponseEntity<>(userDto, HttpStatus.OK);
    }

}
