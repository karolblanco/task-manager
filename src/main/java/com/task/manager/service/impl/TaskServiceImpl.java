package com.task.manager.service.impl;

import com.task.manager.controller.dto.TaskDto;
import com.task.manager.entity.Task;
import com.task.manager.mapper.TaskMapper;
import com.task.manager.repository.TaskRepository;
import com.task.manager.service.TaskService;
import com.task.manager.specification.TaskSpecifications;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

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

    @Override
    public List<Task> findTask(String title) {
        return this.taskRepository.findByTitle(title);
    }

    @Override
    public Page<TaskDto> findAllTask(String title, String status, Pageable pageable) {

        Specification<Task> spec = Specification.where(
                        TaskSpecifications.hasStatus(status))
                                .and(TaskSpecifications.hasTitle(title));

        return taskRepository.findAll(spec, pageable)
                .map(taskMapper::toDto);


    }


    @Override
    public TaskDto updateTask(Long id, TaskDto taskDto) throws Exception {
      Task currentTask = this.taskRepository.findById(id)
              .orElseThrow( () -> new Exception("Task not found with id: " + id));

       this.taskMapper.updateEntityFromDto(taskDto, currentTask);
       Task savedTask = taskRepository.save(currentTask);
        return taskMapper.toDto(savedTask);
    }

    @Override
    public void deleteTask(Long id) {
        taskRepository.deleteById(id);
    }
}
