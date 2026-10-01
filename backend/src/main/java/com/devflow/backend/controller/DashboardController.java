package com.devflow.backend.controller;

import com.devflow.backend.dto.dashboard.DashboardResponse;
import com.devflow.backend.service.DashboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/projects")
@RequiredArgsConstructor
public class DashboardController {

    private final DashboardService dashboardService;

    @GetMapping("/{projectId}/dashboard")
    public DashboardResponse getProjectDashboard(
            @PathVariable Long projectId,
            Authentication authentication
    ) {

        String userEmail = authentication.getName();

        return dashboardService.getProjectDashboard(
                projectId,
                userEmail
        );
    }
}