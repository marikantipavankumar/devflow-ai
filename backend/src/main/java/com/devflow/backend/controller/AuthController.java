package com.devflow.backend.controller;

import com.devflow.backend.dto.LoginRequest;
import com.devflow.backend.dto.LoginResponse;
import com.devflow.backend.dto.LoginUserResponse;
import com.devflow.backend.security.CustomUserDetails;
import com.devflow.backend.security.JwtService;
import com.devflow.backend.security.TokenBlacklistService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final TokenBlacklistService tokenBlacklistService;

    public AuthController(AuthenticationManager authenticationManager, JwtService jwtService, TokenBlacklistService tokenBlacklistService) {
        this.authenticationManager = authenticationManager;
        this.jwtService=jwtService;
        this.tokenBlacklistService=tokenBlacklistService;
    }

    @PostMapping("/login")
    public LoginResponse login( @Valid @RequestBody LoginRequest request) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        CustomUserDetails customUserDetails =
                (CustomUserDetails) authentication.getPrincipal();

        String token = jwtService.generateToken(customUserDetails);

        return new LoginResponse(
                token,
                customUserDetails.getUsername(),
                customUserDetails.getActualUsername()
        );
    }

    @GetMapping("/me")
    public LoginUserResponse getCurrentUser(Authentication authentication) {

        CustomUserDetails userDetails =
                (CustomUserDetails) authentication.getPrincipal();

        return new LoginUserResponse(
                userDetails.getUsername(),
                userDetails.getActualUsername()
        );
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(
            HttpServletRequest request) {

        String authHeader = request.getHeader("Authorization");

        if (authHeader != null &&
                authHeader.startsWith("Bearer ")) {

            String jwt = authHeader.substring(7);

            String tokenId = jwtService.extractTokenId(jwt);

            tokenBlacklistService.blacklistToken(tokenId);
        }

        return ResponseEntity.noContent().build();
    }


}

