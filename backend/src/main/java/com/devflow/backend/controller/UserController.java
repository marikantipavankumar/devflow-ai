package com.devflow.backend.controller;


import com.devflow.backend.dto.CreateUserRequest;
import com.devflow.backend.entity.User;
import com.devflow.backend.service.UserService;
import jakarta.validation.Valid;
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
    public List<User> getAllUsers(){
        return userService.getAllUsers();
    }

    @PostMapping("/register")
    public User registerUser(@Valid @RequestBody CreateUserRequest request) {
        return userService.registerUser(request);
    }
}
