package com.devflow.backend.mapper;

import com.devflow.backend.dto.TaskResponse;
import com.devflow.backend.entity.Task;

public class TaskMapper {

    public static TaskResponse toResponse(Task task) {

        return new TaskResponse(
                task.getId(),
                task.getTitle(),
                task.getDescription(),
                task.getStatus(),
                task.getPriority(),
                task.getProject().getId(),
                task.getAssignedTo() != null
                        ? task.getAssignedTo().getId()
                        : null,
                task.getAssignedTo() != null
                        ? task.getAssignedTo().getUsername()
                        : null,
                task.getCreatedAt(),
                task.getUpdatedAt()
        );
    }
}