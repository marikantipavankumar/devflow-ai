package com.devflow.backend.controller;

import com.devflow.backend.dto.*;
import com.devflow.backend.service.ProjectService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/projects")
public class ProjectController {

    private final ProjectService projectService;

    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProjectResponse createProject(
            @Valid @RequestBody CreateProjectRequest request,
            Authentication authentication) {

        String email = authentication.getName();

        return projectService.createProject(
                email,
                request
        );
    }

    @GetMapping
    public List<ProjectResponse> getMyProjects(
            Authentication authentication) {

        String email = authentication.getName();

        return projectService.getMyProjects(email);
    }

    @GetMapping("/{id}")
    public ProjectResponse getProjectById(
            @PathVariable Long id,
            Authentication authentication) {

        String email = authentication.getName();

        return projectService.getProjectById(
                id,
                email
        );
    }

    @PutMapping("/{id}")
    public ProjectResponse updateProject(
            @PathVariable Long id,
            @Valid @RequestBody UpdateProjectRequest request,
            Authentication authentication) {

        String email = authentication.getName();

        return projectService.updateProject(
                id,
                email,
                request
        );
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteProject(
            @PathVariable Long id,
            Authentication authentication) {

        String email = authentication.getName();

        projectService.deleteProject(
                id,
                email
        );
    }

    @PatchMapping("/{id}/status")
    public ProjectResponse updateProjectStatus(
            @PathVariable Long id,
            @Valid @RequestBody UpdateProjectStatusRequest request,
            Authentication authentication) {

        String email = authentication.getName();

        return projectService.updateProjectStatus(
                id,
                email,
                request
        );
    }

    @PostMapping("/{projectId}/members")
    public ProjectMemberResponse addMember(
            @PathVariable Long projectId,
            @Valid @RequestBody AddProjectMemberRequest request,
            Authentication authentication) {

        String ownerEmail = authentication.getName();

        return projectService.addMember(
                projectId,
                ownerEmail,
                request
        );
    }

    @GetMapping("/{projectId}/members")
    public List<ProjectMemberResponse> getMembers(
            @PathVariable Long projectId,
            Authentication authentication) {

        String userEmail = authentication.getName();

        return projectService.getMembers(
                projectId,
                userEmail
        );
    }

    @DeleteMapping("/{projectId}/members/{userId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeMember(
            @PathVariable Long projectId,
            @PathVariable Long userId,
            Authentication authentication) {

        String ownerEmail = authentication.getName();

        projectService.removeMember(
                projectId,
                userId,
                ownerEmail
        );
    }

    @PatchMapping("/{projectId}/members/{userId}/role")
    public ProjectMemberResponse updateMemberRole(
            @PathVariable Long projectId,
            @PathVariable Long userId,
            @Valid @RequestBody UpdateProjectMemberRoleRequest request,
            Authentication authentication) {

        String ownerEmail = authentication.getName();

        return projectService.updateMemberRole(
                projectId,
                userId,
                ownerEmail,
                request
        );
    }

}