package com.devflow.backend.service;

import com.devflow.backend.dto.ActivityLogResponse;
import com.devflow.backend.entity.ActivityAction;

import java.util.List;

public interface ActivityLogService {

    List<ActivityLogResponse> getProjectActivities(
            Long projectId,
            String userEmail
    );

    void logActivity(
            Long projectId,
            String userEmail,
            ActivityAction action,
            String description
    );


}
