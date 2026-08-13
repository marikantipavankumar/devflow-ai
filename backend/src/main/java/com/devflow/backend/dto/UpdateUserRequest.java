package com.devflow.backend.dto;

import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateUserRequest {

    @Size(max = 100)
    private String firstName;

    @Size(max = 100)
    private String lastName;

    @Size(max = 50)
    private String username;

    @Size(max = 15)
    private String phoneNumber;

    @Size(max = 500)
    private String profileImage;

    @Size(max = 500)
    private String bio;
}