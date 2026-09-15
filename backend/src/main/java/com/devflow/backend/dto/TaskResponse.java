package com.devflow.backend.dto;

import com.devflow.backend.entity.TaskPriority;
import com.devflow.backend.entity.TaskStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class TaskResponse {

    private Long id;
    private String title;
    private String description;
    private TaskStatus status;
    private TaskPriority priority;

    private Long projectId;

    private Long assignedToId;
    private String assignedToUsername;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}