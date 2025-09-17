package com.task.manager.repository;

import com.task.manager.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface TaskRepository extends JpaRepository<Task, Long >, JpaSpecificationExecutor<Task> {

    @Override
    Optional<Task> findById(Long id);
    Optional<Task> findByUserIdAndId(Long userId, Long id);
    Optional<Task> findByTitleAndUserId(String title, Long userId);
    boolean existsByTitleAndUserId(String title, Long userId);

}