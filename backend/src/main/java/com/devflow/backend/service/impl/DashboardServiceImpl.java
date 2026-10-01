package com.devflow.backend.service.impl;

import com.devflow.backend.dto.ActivityLogResponse;
import com.devflow.backend.dto.dashboard.DashboardResponse;
import com.devflow.backend.entity.Project;
import com.devflow.backend.entity.ProjectMember;
import com.devflow.backend.entity.Task;
import com.devflow.backend.entity.TaskPriority;
import com.devflow.backend.entity.TaskStatus;
import com.devflow.backend.entity.User;
import com.devflow.backend.exception.ResourceNotFoundException;
import com.devflow.backend.repository.ProjectMemberRepository;
import com.devflow.backend.repository.ProjectRepository;
import com.devflow.backend.repository.TaskRepository;
import com.devflow.backend.repository.UserRepository;
import com.devflow.backend.service.ActivityLogService;
import com.devflow.backend.service.DashboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DashboardServiceImpl implements DashboardService {

    private final ProjectRepository projectRepository;
    private final TaskRepository taskRepository;
    private final ProjectMemberRepository projectMemberRepository;
    private final UserRepository userRepository;
    private final ActivityLogService activityLogService;

    @Override
    public DashboardResponse getProjectDashboard(
            Long projectId,
            String userEmail
    ) {

        // Find project
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Project not found with id: " + projectId
                        )
                );

        // Find authenticated user
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "User not found"
                        )
                );

        // Check project access
        boolean isOwner = project.getOwner()
                .getEmail()
                .equals(userEmail);

        boolean isMember = projectMemberRepository
                .existsByProjectAndUser(project, user);

        if (!isOwner && !isMember) {
            throw new RuntimeException(
                    "You do not have access to this project"
            );
        }

        // Get all tasks
        List<Task> tasks = taskRepository.findByProject(project);

        // Total tasks
        long totalTasks = tasks.size();

        // Task status counts
        long todoTasks = tasks.stream()
                .filter(task -> task.getStatus() == TaskStatus.TODO)
                .count();

        long inProgressTasks = tasks.stream()
                .filter(task -> task.getStatus() == TaskStatus.IN_PROGRESS)
                .count();

        long completedTasks = tasks.stream()
                .filter(task -> task.getStatus() == TaskStatus.COMPLETED)
                .count();

        // Task priority counts
        long lowPriorityTasks = tasks.stream()
                .filter(task -> task.getPriority() == TaskPriority.LOW)
                .count();

        long mediumPriorityTasks = tasks.stream()
                .filter(task -> task.getPriority() == TaskPriority.MEDIUM)
                .count();

        long highPriorityTasks = tasks.stream()
                .filter(task -> task.getPriority() == TaskPriority.HIGH)
                .count();

        // Assigned / unassigned tasks
        long assignedTasks = tasks.stream()
                .filter(task -> task.getAssignedTo() != null)
                .count();

        long unassignedTasks = tasks.stream()
                .filter(task -> task.getAssignedTo() == null)
                .count();

        // Project members
        List<ProjectMember> members =
                projectMemberRepository.findByProject(project);

        long totalMembers = members.size();

        // Recent activities
        List<ActivityLogResponse> recentActivities =
                activityLogService.getProjectActivities(
                        projectId,
                        userEmail
                );

        // Build dashboard response
        return new DashboardResponse(
                project.getId(),
                project.getName(),
                project.getStatus(),

                totalTasks,

                todoTasks,
                inProgressTasks,
                completedTasks,

                lowPriorityTasks,
                mediumPriorityTasks,
                highPriorityTasks,

                assignedTasks,
                unassignedTasks,

                totalMembers,

                recentActivities
        );
    }
}