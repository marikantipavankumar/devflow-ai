package com.devflow.backend.service.impl;

import com.devflow.backend.dto.ActivityLogResponse;
import com.devflow.backend.entity.ActivityLog;
import com.devflow.backend.entity.Project;
import com.devflow.backend.entity.User;
import com.devflow.backend.exception.AccessDeniedException;
import com.devflow.backend.exception.ResourceNotFoundException;
import com.devflow.backend.mapper.ActivityLogMapper;
import com.devflow.backend.repository.ActivityLogRepository;
import com.devflow.backend.repository.ProjectMemberRepository;
import com.devflow.backend.repository.ProjectRepository;
import com.devflow.backend.repository.UserRepository;
import com.devflow.backend.service.ActivityLogService;
import com.devflow.backend.entity.ActivityAction;
import com.devflow.backend.entity.ActivityLog;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ActivityLogServiceImpl implements ActivityLogService {

    private final ActivityLogRepository activityLogRepository;
    private final ProjectRepository projectRepository;
    private final ProjectMemberRepository projectMemberRepository;
    private final UserRepository userRepository;

    @Override
    public List<ActivityLogResponse> getProjectActivities(
            Long projectId,
            String userEmail) {

        Project project = projectRepository.findById(projectId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Project not found with ID: " + projectId
                        )
                );

        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "User not found with email: " + userEmail
                        )
                );

        boolean hasAccess =
                project.getOwner().getEmail().equals(userEmail)
                        || projectMemberRepository
                        .existsByProjectAndUser(project, user);

        if (!hasAccess) {
            throw new AccessDeniedException(
                    "You are not a member of this project"
            );
        }

        List<ActivityLog> activities =
                activityLogRepository
                        .findByProjectOrderByCreatedAtDesc(project);

        return activities.stream()
                .map(ActivityLogMapper::toResponse)
                .toList();
    }

    @Override
    public void logActivity(
            Long projectId,
            String userEmail,
            ActivityAction action,
            String description) {

        Project project = projectRepository.findById(projectId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Project not found with ID: " + projectId
                        )
                );

        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "User not found with email: " + userEmail
                        )
                );

        ActivityLog activityLog = ActivityLog.builder()
                .project(project)
                .user(user)
                .action(action)
                .description(description)
                .build();

        activityLogRepository.save(activityLog);
    }


}