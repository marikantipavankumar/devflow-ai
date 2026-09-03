package com.devflow.backend.dto;

import com.devflow.backend.entity.ProjectMemberRole;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateProjectMemberRoleRequest {

    @NotNull(message = "Role is required")
    private ProjectMemberRole role;
}