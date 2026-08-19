package com.devflow.backend.service.impl;

import com.devflow.backend.dto.CreateProjectRequest;
import com.devflow.backend.dto.ProjectResponse;
import com.devflow.backend.dto.UpdateProjectRequest;
import com.devflow.backend.entity.Project;
import com.devflow.backend.entity.ProjectStatus;
import com.devflow.backend.entity.User;
import com.devflow.backend.exception.AccessDeniedException;
import com.devflow.backend.exception.BusinessRuleException;
import com.devflow.backend.exception.ResourceNotFoundException;
import com.devflow.backend.mapper.ProjectMapper;
import com.devflow.backend.repository.ProjectRepository;
import com.devflow.backend.repository.UserRepository;
import com.devflow.backend.service.ProjectService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProjectServiceImpl implements ProjectService {

    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;
    private final ProjectMapper projectMapper;

    public ProjectServiceImpl(
            ProjectRepository projectRepository,
            UserRepository userRepository,
            ProjectMapper projectMapper) {

        this.projectRepository = projectRepository;
        this.userRepository = userRepository;
        this.projectMapper = projectMapper;
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

    }