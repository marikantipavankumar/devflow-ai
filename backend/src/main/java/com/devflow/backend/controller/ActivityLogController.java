package com.devflow.backend.controller;

import com.devflow.backend.dto.ActivityLogResponse;
import com.devflow.backend.service.ActivityLogService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/projects")
@RequiredArgsConstructor
public class ActivityLogController {

    private final ActivityLogService activityLogService;

    @GetMapping("/{projectId}/activities")
    public List<ActivityLogResponse> getProjectActivities(
            @PathVariable Long projectId,
            Authentication authentication) {

        String userEmail = authentication.getName();

        return activityLogService.getProjectActivities(
                projectId,
                userEmail
        );
    }
}