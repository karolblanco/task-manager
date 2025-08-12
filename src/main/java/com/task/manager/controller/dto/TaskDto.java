package com.task.manager.controller.dto;

import com.task.manager.entity.TaskStatus;

import java.time.LocalDate;

public record TaskDto(String title, String description, LocalDate dueDate, TaskStatus status ) {
}
