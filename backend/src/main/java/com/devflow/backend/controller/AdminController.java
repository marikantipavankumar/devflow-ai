package com.devflow.backend.controller;

import com.devflow.backend.dto.UpdateRoleRequest;
import com.devflow.backend.dto.UpdateUserStatusRequest;
import com.devflow.backend.dto.UserResponse;
import com.devflow.backend.service.UserService;
import jakarta.validation.Valid;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.data.jpa.domain.AbstractPersistable_.id;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    private final UserService userService;

    public AdminController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/test")
    public String adminTest() {
        return "Welcome Admin! You have access to this endpoint.";
    }

    @GetMapping("/users")
    public List<UserResponse> getAllUsers() {
        return userService.getAllUsers();
    }

    @PatchMapping("/users/{id}/role")
    public UserResponse updateUserRole(
            @PathVariable Long id,
            @Valid @RequestBody UpdateRoleRequest request,
            Authentication authentication) {

        String adminEmail = authentication.getName();

        return userService.updateUserRole(
                id,
                request.getRole(),
                adminEmail
        );
    }
    @PatchMapping("/users/{id}/status")
    public UserResponse updateUserStatus(
            @PathVariable Long id,
            @Valid @RequestBody UpdateUserStatusRequest request,
            Authentication authentication) {

        String adminEmail = authentication.getName();

        return userService.updateUserStatus(
                id,
                request.getActive(),
                adminEmail
        );
    }


}