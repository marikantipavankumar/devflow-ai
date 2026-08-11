package com.devflow.backend.controller;


import com.devflow.backend.dto.CreateUserRequest;
import com.devflow.backend.dto.UserResponse;
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
    public List<UserResponse> getAllUsers(){
        System.out.println("🔥 USER CONTROLLER HIT");

        List<UserResponse> users = userService.getAllUsers();

        System.out.println("🔥 USERS FETCHED: " + users.size());

        return userService.getAllUsers();
    }

    @PostMapping("/register")
    public UserResponse registerUser(@Valid @RequestBody CreateUserRequest request) {
        return userService.registerUser(request);
    }
}
