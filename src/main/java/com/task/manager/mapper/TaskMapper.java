package com.task.manager.mapper;

import com.task.manager.controller.dto.TaskDto;
import com.task.manager.entity.Task;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface TaskMapper {
    Task toEntity(TaskDto dto);
    TaskDto toDto(Task entity);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntityFromDto(TaskDto dto, @MappingTarget Task entity);
}
