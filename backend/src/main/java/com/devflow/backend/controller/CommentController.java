package com.devflow.backend.controller;
import com.devflow.backend.dto.CommentResponse;
import com.devflow.backend.dto.CreateCommentRequest;
import com.devflow.backend.dto.UpdateCommentRequest;
import com.devflow.backend.service.ProjectService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/projects")
@RequiredArgsConstructor
public class CommentController {

    private final ProjectService projectService;

    @PostMapping("/{projectId}/tasks/{taskId}/comments")
    public CommentResponse createComment(
            @PathVariable Long projectId,
            @PathVariable Long taskId,
            @Valid @RequestBody CreateCommentRequest request,
            Authentication authentication) {

        String userEmail = authentication.getName();

        return projectService.createComment(
                projectId,
                taskId,
                userEmail,
                request
        );
    }

    @GetMapping("/{projectId}/tasks/{taskId}/comments")
    public List<CommentResponse> getTaskComments(
            @PathVariable Long projectId,
            @PathVariable Long taskId,
            Authentication authentication) {

        String userEmail = authentication.getName();

        return projectService.getTaskComments(
                projectId,
                taskId,
                userEmail
        );
    }

    @PutMapping("/{projectId}/tasks/{taskId}/comments/{commentId}")
    public CommentResponse updateComment(
            @PathVariable Long projectId,
            @PathVariable Long taskId,
            @PathVariable Long commentId,
            @Valid @RequestBody UpdateCommentRequest request,
            Authentication authentication) {

        String userEmail = authentication.getName();

        return projectService.updateComment(
                projectId,
                taskId,
                commentId,
                userEmail,
                request
        );
    }

    @DeleteMapping("/{projectId}/tasks/{taskId}/comments/{commentId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteComment(
            @PathVariable Long projectId,
            @PathVariable Long taskId,
            @PathVariable Long commentId,
            Authentication authentication) {

        String userEmail = authentication.getName();

        projectService.deleteComment(
                projectId,
                taskId,
                commentId,
                userEmail
        );
    }
}