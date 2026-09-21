package com.devflow.backend.dto;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AssignTaskRequest {

    @NotNull(message = "User ID is required")
    private Long userId;
}
