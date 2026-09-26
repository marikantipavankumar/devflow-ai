package com.devflow.backend.mapper;

import com.devflow.backend.dto.CommentResponse;
import com.devflow.backend.entity.Comment;

public class CommentMapper {

    public static CommentResponse toResponse(Comment comment) {

        return new CommentResponse(
                comment.getId(),
                comment.getContent(),
                comment.getUser().getId(),
                comment.getUser().getUsername(),
                comment.getTask().getId(),
                comment.getCreatedAt(),
                comment.getUpdatedAt()
        );
    }
}