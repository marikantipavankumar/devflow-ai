package com.devflow.backend.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateCommentRequest {

    @NotBlank(message = "Comment content is required")
    @Size(
            max = 2000,
            message = "Comment cannot exceed 2000 characters"
    )
    private String content;
}