package com.devflow.backend.service;

import com.devflow.backend.dto.*;

import java.util.List;

public interface ProjectService {

    ProjectResponse createProject(String email, CreateProjectRequest request);

    List<ProjectResponse> getMyProjects(
            String email
    );

    ProjectResponse getProjectById(
            Long id,
            String email
    );

    ProjectResponse updateProject(
            Long id,
            String email,
            UpdateProjectRequest request
    );

    void deleteProject(
            Long id,
            String email
    );

    ProjectResponse updateProjectStatus(
            Long id,
            String email,
            UpdateProjectStatusRequest request);

    ProjectMemberResponse addMember(
            Long projectId,
            String ownerEmail,
            AddProjectMemberRequest request
    );

    List<ProjectMemberResponse> getMembers(
            Long projectId,
            String userEmail
    );

    void removeMember(
            Long projectId,
            Long userId,
            String ownerEmail
    );

    ProjectMemberResponse updateMemberRole(
            Long projectId,
            Long userId,
            String ownerEmail,
            UpdateProjectMemberRoleRequest request
    );
}