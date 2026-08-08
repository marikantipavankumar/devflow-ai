package com.devflow.backend.service;

import com.devflow.backend.dto.CreateUserRequest;
import com.devflow.backend.entity.User;

import java.util.List;

public interface UserService {
     List<User> getAllUsers();
    User getUserById(Long id);

    User registerUser(CreateUserRequest request);
}
