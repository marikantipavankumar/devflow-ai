package com.devflow.backend.repository;

import com.devflow.backend.entity.ActivityLog;
import com.devflow.backend.entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ActivityLogRepository
        extends JpaRepository<ActivityLog, Long> {
    List<ActivityLog> findByProjectOrderByCreatedAtDesc(
            Project project
    );
}