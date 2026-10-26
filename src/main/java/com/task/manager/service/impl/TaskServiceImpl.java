package com.task.manager.service.impl;

import com.task.manager.controller.dto.TaskDto;
import com.task.manager.entity.Task;
import com.task.manager.entity.User;
import com.task.manager.exception.ApiException;
import com.task.manager.exception.ErrorMessage;
import com.task.manager.mapper.TaskMapper;
import com.task.manager.repository.TaskRepository;
import com.task.manager.repository.UserRepository;
import com.task.manager.service.TaskService;
import com.task.manager.specification.TaskSpecifications;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
public class TaskServiceImpl implements TaskService {
    private  TaskRepository taskRepository;
    private  TaskMapper taskMapper;
    private UserRepository userRepository;
    public TaskServiceImpl( TaskRepository taskRepository, TaskMapper taskMapper, UserRepository userRepository) {
       this.taskRepository = taskRepository;
       this.taskMapper = taskMapper;
       this.userRepository = userRepository;
    }

    @Override
    public TaskDto createTask(TaskDto taskDto, Long userId) {
        User user = findUserById(userId);
        validateUniqueTitle(taskDto.title(), userId);
        Task taskEntity = this.taskMapper.toEntity(taskDto);
        taskEntity.setUser(user);
        taskEntity = this.taskRepository.save(taskEntity);
        return taskMapper.toDto(taskEntity);
    }

    @Override
    public TaskDto getTaskByTitle(String title, Long userId) {
        User user = findUserById(userId);
        Task task = this.taskRepository.findByTitleAndUserId(title, user.getId())
                .orElseThrow(() -> new ApiException(ErrorMessage.TASK_NOT_FOUND));
        return taskMapper.toDto(task);
    }

    @Override
    public TaskDto getTaskById(Long id, Long userId) {
        Task task = findTaskById(id, userId);
        return taskMapper.toDto(task);
    }

    @Override
    public Page<TaskDto> findAllTask(String title, String status, Pageable pageable, Long userId) {

        Specification<Task> spec = Specification.where(
                        TaskSpecifications.hasStatus(status))
                                .and(TaskSpecifications.hasTitle(title))
                                .and(TaskSpecifications.hasUserId(userId));

        return taskRepository.findAll(spec, pageable)
                .map(taskMapper::toDto);

    }

    @Override
    public TaskDto updateTask(Long id, TaskDto taskDto, Long userId) {
        Task currentTask = findTaskById(id, userId);
        validateUniqueTitle(taskDto.title(), userId);
        this.taskMapper.updateEntityFromDto(taskDto, currentTask);
        Task savedTask = taskRepository.save(currentTask);
        return taskMapper.toDto(savedTask);
    }

    private Task findTaskById(Long id, Long userId){
        return this.taskRepository.findByUserIdAndId(userId, id)
                .orElseThrow(() -> new ApiException(ErrorMessage.TASK_NOT_FOUND));
    }

    @Override
    public void deleteTask(Long id, Long userId) {
        Task task = findTaskById(id,userId);
        taskRepository.deleteById(task.getId());
    }

    private void validateUniqueTitle(String title, Long userId) {
        if (taskRepository.existsByTitleAndUserId(title, userId)) {
            throw new ApiException(ErrorMessage.TASK_ALREADY_EXISTS);
        }
    }

    private User findUserById(Long userId){
        return userRepository.findById(userId)
                .orElseThrow( () -> new UsernameNotFoundException("User not found: " + userId));
    }
}
