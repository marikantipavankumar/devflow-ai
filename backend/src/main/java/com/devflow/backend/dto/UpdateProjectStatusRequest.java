package com.devflow.backend.dto;

import com.devflow.backend.entity.ProjectStatus;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateProjectStatusRequest {

    @NotNull(message = "Project status is required")
    private ProjectStatus status;
}