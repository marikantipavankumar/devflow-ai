package com.devflow.backend.mapper;

import com.devflow.backend.dto.ActivityLogResponse;
import com.devflow.backend.entity.ActivityLog;

public class ActivityLogMapper {

    public static ActivityLogResponse toResponse(
            ActivityLog activityLog) {

        return new ActivityLogResponse(
                activityLog.getId(),
                activityLog.getAction(),
                activityLog.getDescription(),
                activityLog.getUser().getId(),
                activityLog.getUser().getUsername(),
                activityLog.getProject().getId(),
                activityLog.getCreatedAt()
        );
    }
}