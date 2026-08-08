package com.devflow.backend.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateUserRequest {

    @NotBlank
    private String firstName;

    @NotBlank
    private String lastName;

    @NotBlank
    private String username;

    @Email
    @NotBlank
    private String email;

    @Size(min = 8)
    private String password;

    @NotBlank(message = "Phone number is required")
    @Pattern(
            regexp = "\\d{10}",
            message = "Phone number must contain exactly 10 digits"
    )
    private String phoneNumber;
}