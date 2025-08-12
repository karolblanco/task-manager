package com.task.manager.service;


import com.task.manager.controller.dto.TaskDto;
import com.task.manager.entity.Task;

public interface TaskService {
    Task createTask(TaskDto taskDto);
}
