package com.devflow.backend.dto;

import com.devflow.backend.entity.ProjectMemberRole;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class ProjectMemberResponse {

    private Long userId;
    private String username;
    private String email;
    private ProjectMemberRole role;
    private LocalDateTime joinedAt;
}