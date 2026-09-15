package com.devflow.backend.repository;

import com.devflow.backend.entity.Project;
import com.devflow.backend.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TaskRepository extends JpaRepository<Task, Long> {

    List<Task> findByProject(Project project);

    Optional<Task> findByIdAndProject(
            Long id,
            Project project
    );


}