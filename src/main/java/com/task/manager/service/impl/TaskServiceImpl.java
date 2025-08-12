package com.task.manager.service.impl;

import com.task.manager.controller.dto.TaskDto;
import com.task.manager.entity.Task;
import com.task.manager.mapper.TaskMapper;
import com.task.manager.repository.TaskRepository;
import com.task.manager.service.TaskService;
import org.springframework.stereotype.Service;

@Service
public class TaskServiceImpl implements TaskService {
    private  TaskRepository taskRepository;
    private  TaskMapper taskMapper;
    public TaskServiceImpl( TaskRepository taskRepository, TaskMapper taskMapper) {
       this.taskRepository = taskRepository;
       this.taskMapper = taskMapper;
    }

    @Override
    public Task createTask(TaskDto taskDto) {
        Task taskEntity = this.taskMapper.toEntity(taskDto);
        taskEntity = this.taskRepository.save(taskEntity);
        return taskEntity;
    }
}
