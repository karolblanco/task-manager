package com.task.manager.service;


import com.task.manager.controller.dto.TaskDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface TaskService {
    TaskDto createTask(TaskDto taskDto, Long userId);

    TaskDto getTaskByTitle(String title, Long userId);

    TaskDto getTaskById(Long id, Long userId);

    Page<TaskDto> findAllTask(String title, String status, Pageable pageable, Long userId);

    TaskDto updateTask(Long id, TaskDto taskDto, Long userId);

    void deleteTask(Long id, Long userId);
}
