package com.devflow.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class CommentResponse {

    private Long id;
    private String content;

    private Long userId;
    private String username;

    private Long taskId;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}