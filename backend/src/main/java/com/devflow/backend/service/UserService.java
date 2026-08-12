package com.devflow.backend.service;

import com.devflow.backend.dto.CreateUserRequest;
import com.devflow.backend.dto.UserResponse;
import com.devflow.backend.entity.User;

import java.util.List;

public interface UserService {
     List<UserResponse> getAllUsers();
    User getUserById(Long id);

    UserResponse registerUser(CreateUserRequest request);

    User getUserByEmail(String email);
}
