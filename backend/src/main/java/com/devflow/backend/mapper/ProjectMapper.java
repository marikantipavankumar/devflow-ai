package com.devflow.backend.mapper;

import com.devflow.backend.dto.ProjectResponse;
import com.devflow.backend.entity.Project;
import org.springframework.stereotype.Component;

@Component
public class ProjectMapper {

    public ProjectResponse toResponse(Project project) {

        return new ProjectResponse(
                project.getId(),
                project.getName(),
                project.getDescription(),
                project.getStatus(),
                project.getOwner().getId(),
                project.getOwner().getUsername(),
                project.getCreatedAt(),
                project.getUpdatedAt()
        );
    }
}