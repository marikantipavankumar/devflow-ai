package com.devflow.backend.service;

import com.devflow.backend.dto.dashboard.DashboardResponse;

public interface DashboardService {

    DashboardResponse getProjectDashboard(
            Long projectId,
            String userEmail
    );
}