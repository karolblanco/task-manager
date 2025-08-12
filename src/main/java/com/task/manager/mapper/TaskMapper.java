package com.task.manager.mapper;

import com.task.manager.controller.dto.TaskDto;
import com.task.manager.entity.Task;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

@Mapper(componentModel = "spring")
public interface TaskMapper {
    Task toEntity(TaskDto dto);
    TaskDto toDto(Task entity);
}
