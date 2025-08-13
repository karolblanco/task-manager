package com.task.manager.controller.dto;

import com.task.manager.entity.TaskStatus;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public record TaskDto(
        @NotBlank(message = "title can't be empty")
        @Size(max = 100, message = "title can't have more than 100 characters")
        String title,
        @NotBlank(message = "Description can't be empty")
        String description,
        @NotNull(message = "Due date is mandatory")
        @FutureOrPresent(message = "The due date can't be in the past")
        LocalDate dueDate,
        TaskStatus status
) {
}
