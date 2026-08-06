package com.devflow.backend.service.impl;

import com.devflow.backend.entity.User;
import com.devflow.backend.repository.UserRepository;
import com.devflow.backend.service.UserService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository; // Indicates that UserServiceImpl depends upon the UserRepository
    UserServiceImpl(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(()->new RuntimeException("User not found with ID:"+id));
    }

    @Override
    public User createUser(User user) {
        return userRepository.save(user);
    }
}
