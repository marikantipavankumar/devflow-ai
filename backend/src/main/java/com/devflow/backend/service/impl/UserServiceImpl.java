package com.devflow.backend.service.impl;

import com.devflow.backend.dto.CreateUserRequest;
import com.devflow.backend.dto.UserResponse;
import com.devflow.backend.entity.User;
import com.devflow.backend.mapper.UserMapper;
import com.devflow.backend.repository.UserRepository;
import com.devflow.backend.service.UserService;
import jakarta.persistence.PrePersist;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository; // Indicates that UserServiceImpl depends upon the UserRepository
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;
    UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, UserMapper userMapper){
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.userMapper = userMapper;
    }

    @Override
    public List<UserResponse> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(userMapper::toResponse)
                .toList();
    }

    @Override
    public User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(()->new RuntimeException("User not found with ID:"+id));
    }

    @Override
    public UserResponse registerUser(CreateUserRequest request) {
        if(userRepository.existsByEmail(request.getEmail())){
            throw new RuntimeException("Email Already Exists!...");
        }

        if (userRepository.existsByUsername(request.getUsername())) {
            throw new RuntimeException("Username already exists.");
        }


        User user = User.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .username(request.getUsername())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .phoneNumber(request.getPhoneNumber())
                .build();

        user.setIsVerified(false);
        user.setIsActive(true);
        user.setCreatedAt(LocalDateTime.now());
        user.setUpdatedAt(LocalDateTime.now());

        User usermapper=userRepository.save(user);
         return userMapper.toResponse(usermapper);
    }

    @Override
    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email).
                orElseThrow(()->new RuntimeException("User not Found"));
    }

}
