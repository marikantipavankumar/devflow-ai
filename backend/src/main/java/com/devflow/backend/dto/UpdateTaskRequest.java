package com.devflow.backend.dto;

import com.devflow.backend.entity.TaskPriority;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateTaskRequest {

    @NotBlank(message = "Task title is required")
    @Size(max = 200, message = "Task title cannot exceed 200 characters")
    private String title;

    @Size(max = 2000, message = "Task description cannot exceed 2000 characters")
    private String description;

    private TaskPriority priority;
}