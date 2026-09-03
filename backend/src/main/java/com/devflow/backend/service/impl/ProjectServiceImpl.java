package com.devflow.backend.service.impl;

import com.devflow.backend.dto.*;
import com.devflow.backend.entity.Project;
import com.devflow.backend.entity.ProjectStatus;
import com.devflow.backend.entity.User;
import com.devflow.backend.exception.AccessDeniedException;
import com.devflow.backend.exception.BusinessRuleException;
import com.devflow.backend.exception.ResourceAlreadyExistsException;
import com.devflow.backend.exception.ResourceNotFoundException;
import com.devflow.backend.mapper.ProjectMapper;
import com.devflow.backend.repository.ProjectMemberRepository;
import com.devflow.backend.repository.ProjectRepository;
import com.devflow.backend.repository.UserRepository;
import com.devflow.backend.service.ProjectService;
import org.springframework.stereotype.Service;
import com.devflow.backend.entity.ProjectMember;
import java.util.List;

@Service
public class ProjectServiceImpl implements ProjectService {

    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;
    private final ProjectMapper projectMapper;
    private final ProjectMemberRepository projectMemberRepository;

    public ProjectServiceImpl(
            ProjectRepository projectRepository,
            UserRepository userRepository,
            ProjectMapper projectMapper, ProjectMemberRepository projectMemberRepository) {

        this.projectRepository = projectRepository;
        this.userRepository = userRepository;
        this.projectMapper = projectMapper;
        this.projectMemberRepository = projectMemberRepository;
    }
    @Override
    public ProjectResponse createProject(
            String email,
            CreateProjectRequest request) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "User not found"
                        )
                );

        Project project = Project.builder()
                .name(request.getName())
                .description(request.getDescription())
                .status(ProjectStatus.PLANNING)
                .owner(user)
                .build();

        Project savedProject = projectRepository.save(project);

        return projectMapper.toResponse(savedProject);
    }

    @Override
    public List<ProjectResponse> getMyProjects(String email) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "User not found"
                        )
                );

        return projectRepository.findByOwner(user)
                .stream()
                .map(projectMapper::toResponse)
                .toList();
    }

    @Override
    public ProjectResponse getProjectById(
            Long id,
            String email) {

        Project project = projectRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Project not found with ID: " + id
                        )
                );

        if (!project.getOwner().getEmail().equals(email)) {
            throw new AccessDeniedException(
                    "You do not have access to this project"
            );
        }

        return projectMapper.toResponse(project);
    }

    @Override
    public ProjectResponse updateProject(
            Long id,
            String email,
            UpdateProjectRequest request) {

        Project project = projectRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Project not found with ID: " + id
                        )
                );

        if (!project.getOwner().getEmail().equals(email)) {
            throw new AccessDeniedException(
                    "You do not have access to this project"
            );
        }

        if (request.getName() != null) {
            project.setName(request.getName());
        }

        if (request.getDescription() != null) {
            project.setDescription(request.getDescription());
        }

        Project updatedProject = projectRepository.save(project);

        return projectMapper.toResponse(updatedProject);
    }

    @Override
    public void deleteProject(
            Long id,
            String email) {

        Project project = projectRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Project not found with ID: " + id
                        )
                );

        if (!project.getOwner().getEmail().equals(email)) {
            throw new AccessDeniedException(
                    "You do not have access to this project"
            );
        }

        projectRepository.delete(project);
    }

    @Override
    public ProjectResponse updateProjectStatus(
            Long id,
            String email,
            UpdateProjectStatusRequest request) {

        Project project = projectRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Project not found with ID: " + id
                        )
                );

        if (!project.getOwner().getEmail().equals(email)) {
            throw new AccessDeniedException(
                    "You do not have access to this project"
            );
        }

        ProjectStatus currentStatus = project.getStatus();
        ProjectStatus newStatus = request.getStatus();

        if (!isValidStatusTransition(currentStatus, newStatus)) {
            throw new BusinessRuleException(
                    "Invalid project status transition from "
                            + currentStatus
                            + " to "
                            + newStatus
            );
        }

        project.setStatus(newStatus);

        Project updatedProject =
                projectRepository.save(project);

        return projectMapper.toResponse(updatedProject);
    }

    private boolean isValidStatusTransition(
            ProjectStatus currentStatus,
            ProjectStatus newStatus) {

        if (currentStatus == newStatus) {
            return true;
        }

        return switch (currentStatus) {

            case PLANNING ->
                    newStatus == ProjectStatus.ACTIVE;

            case ACTIVE ->
                    newStatus == ProjectStatus.COMPLETED;

            case COMPLETED ->
                    newStatus == ProjectStatus.ARCHIVED;

            case ARCHIVED ->
                    false;
        };
    }

    @Override
    public ProjectMemberResponse addMember(
            Long projectId,
            String ownerEmail,
            AddProjectMemberRequest request) {

        Project project = projectRepository.findById(projectId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Project not found with ID: " + projectId
                        )
                );

        if (!project.getOwner().getEmail().equals(ownerEmail)) {
            throw new AccessDeniedException(
                    "Only the project owner can add members"
            );
        }

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "User not found with email: "
                                        + request.getEmail()
                        )
                );

        if (project.getOwner().getId().equals(user.getId())) {
            throw new ResourceAlreadyExistsException(
                    "Project owner is already part of the project"
            );
        }

        if (projectMemberRepository.existsByProjectAndUser(
                project,
                user)) {

            throw new ResourceAlreadyExistsException(
                    "User is already a member of this project"
            );
        }

        ProjectMember member = ProjectMember.builder()
                .project(project)
                .user(user)
                .role(request.getRole())
                .build();

        ProjectMember savedMember =
                projectMemberRepository.save(member);

        return new ProjectMemberResponse(
                savedMember.getUser().getId(),
                savedMember.getUser().getUsername(),
                savedMember.getUser().getEmail(),
                savedMember.getRole(),
                savedMember.getJoinedAt()
        );
    }

    @Override
    public List<ProjectMemberResponse> getMembers(
            Long projectId,
            String userEmail) {

        // 1. Find the project
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Project not found with ID: " + projectId
                        )
                );

        // 2. Check whether the logged-in user is the owner
        if (!project.getOwner().getEmail().equals(userEmail)) {
            throw new AccessDeniedException(
                    "You are not authorized to view project members"
            );
        }

        // 3. Get all members of the project
        List<ProjectMember> members =
                projectMemberRepository.findByProject(project);

        // 4. Convert entities to response DTOs
        return members.stream()
                .map(member -> new ProjectMemberResponse(
                        member.getUser().getId(),
                        member.getUser().getUsername(),
                        member.getUser().getEmail(),
                        member.getRole(),
                        member.getJoinedAt()
                ))
                .toList();
    }

    @Override
    public void removeMember(
            Long projectId,
            Long userId,
            String ownerEmail) {

        // 1. Find the project
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Project not found with ID: " + projectId
                        )
                );

        // 2. Check whether the requester is the project owner
        if (!project.getOwner().getEmail().equals(ownerEmail)) {
            throw new AccessDeniedException(
                    "Only the project owner can remove members"
            );
        }

        // 3. Find the user
        User user = userRepository.findById(userId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "User not found with ID: " + userId
                        )
                );

        // 4. Check whether the user is actually a member
        ProjectMember member =
                projectMemberRepository
                        .findByProjectAndUser(project, user)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "User is not a member of this project"
                                )
                        );

        // 5. Remove the membership
        projectMemberRepository.delete(member);
    }

    @Override
    public ProjectMemberResponse updateMemberRole(
            Long projectId,
            Long userId,
            String ownerEmail,
            UpdateProjectMemberRoleRequest request) {

        // 1. Find the project
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Project not found with ID: " + projectId
                        )
                );

        // 2. Check whether requester is the project owner
        if (!project.getOwner().getEmail().equals(ownerEmail)) {
            throw new AccessDeniedException(
                    "Only the project owner can update member roles"
            );
        }

        // 3. Find the user
        User user = userRepository.findById(userId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "User not found with ID: " + userId
                        )
                );

        // 4. Find the membership
        ProjectMember member =
                projectMemberRepository
                        .findByProjectAndUser(project, user)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "User is not a member of this project"
                                )
                        );

        // 5. Update the role
        member.setRole(request.getRole());

        // 6. Save the updated membership
        ProjectMember updatedMember =
                projectMemberRepository.save(member);

        // 7. Return response
        return new ProjectMemberResponse(
                updatedMember.getUser().getId(),
                updatedMember.getUser().getUsername(),
                updatedMember.getUser().getEmail(),
                updatedMember.getRole(),
                updatedMember.getJoinedAt()
        );
    }

}