package com.devflow.backend.repository;
import com.devflow.backend.entity.Comment;
import com.devflow.backend.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface CommentRepository
        extends JpaRepository<Comment, Long> {

    List<Comment> findByTask(Task task);

    Optional<Comment> findByIdAndTask(
            Long id,
            Task task
    );
}