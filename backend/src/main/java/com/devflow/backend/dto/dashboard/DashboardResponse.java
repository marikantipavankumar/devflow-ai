package com.devflow.backend.dto.dashboard;

import com.devflow.backend.dto.ActivityLogResponse;
import com.devflow.backend.entity.ProjectStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class DashboardResponse {

    private Long projectId;
    private String projectName;
    private ProjectStatus projectStatus;

    private long totalTasks;

    private long todoTasks;
    private long inProgressTasks;
    private long completedTasks;

    private long lowPriorityTasks;
    private long mediumPriorityTasks;
    private long highPriorityTasks;

    private long assignedTasks;
    private long unassignedTasks;

    private long totalMembers;

    private List<ActivityLogResponse> recentActivities;
}