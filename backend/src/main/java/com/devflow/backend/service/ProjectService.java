package com.devflow.backend.service;

import com.devflow.backend.dto.CreateProjectRequest;
import com.devflow.backend.dto.ProjectResponse;
import com.devflow.backend.dto.UpdateProjectRequest;

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
}