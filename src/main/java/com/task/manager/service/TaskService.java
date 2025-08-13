package com.task.manager.service;


import com.task.manager.controller.dto.TaskDto;
import com.task.manager.entity.Task;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface TaskService {
    Task createTask(TaskDto taskDto);

    List<Task> findTask(String title);

    Page<TaskDto> findAllTask(String title, String status, Pageable pageable);

    TaskDto updateTask(Long id, TaskDto taskDto) throws Exception;

    void deleteTask(Long id);
}
