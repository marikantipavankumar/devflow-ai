package com.devflow.backend.controller;


import com.devflow.backend.dto.CreateUserRequest;
import com.devflow.backend.dto.UpdateUserRequest;
import com.devflow.backend.dto.UserResponse;
import com.devflow.backend.entity.User;
import com.devflow.backend.service.UserService;
import jakarta.validation.Valid;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private  final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public List<UserResponse> getAllUsers(){

        return userService.getAllUsers();
    }

    @PutMapping("/me")
    public UserResponse updateUser(@Valid @RequestBody UpdateUserRequest request,Authentication authentication){
        String email=authentication.getName();
        return userService.updateUser(email,request);
    }

    @GetMapping("/me")
    public UserResponse getCurrentUser(Authentication authentication) {

        String email = authentication.getName();

        return userService.getUserByEmail(email);
    }

    @PostMapping("/register")
    public UserResponse registerUser(@Valid @RequestBody CreateUserRequest request) {
        return userService.registerUser(request);
    }
}
