package com.devflow.backend.dto;

import com.devflow.backend.entity.ActivityAction;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class ActivityLogResponse {

    private Long id;

    private ActivityAction action;

    private String description;

    private Long userId;

    private String username;

    private Long projectId;

    private LocalDateTime createdAt;
}