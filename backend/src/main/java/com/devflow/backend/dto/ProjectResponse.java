package com.devflow.backend.dto;

import com.devflow.backend.entity.ProjectStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class ProjectResponse {

    private Long id;
    private String name;
    private String description;
    private ProjectStatus status;

    private Long ownerId;
    private String ownerUsername;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}