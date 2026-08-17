package com.devflow.backend.service;

import com.devflow.backend.dto.ChangePasswordRequest;
import com.devflow.backend.dto.CreateUserRequest;
import com.devflow.backend.dto.UpdateUserRequest;
import com.devflow.backend.dto.UserResponse;
import com.devflow.backend.entity.User;

import java.util.List;

public interface UserService {
     List<UserResponse> getAllUsers();
    User getUserById(Long id);

    UserResponse updateUser(String email, UpdateUserRequest request);

    UserResponse registerUser(CreateUserRequest request);

    UserResponse getUserByEmail(String email);

    void changePassword(
            String email,
            ChangePasswordRequest request
    );
}
