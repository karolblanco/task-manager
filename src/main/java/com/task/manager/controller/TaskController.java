package com.task.manager.controller;

import com.oracle.svm.core.annotate.Delete;
import com.task.manager.controller.dto.TaskDto;
import com.task.manager.entity.Task;
import com.task.manager.service.TaskService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/task")
public class TaskController {
    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @Operation(
            summary = "Create task"
    )
    @PostMapping()
    public ResponseEntity<Task> createTask(@Valid @RequestBody TaskDto taskDto) {
        return  ResponseEntity.status(HttpStatus.CREATED).body(taskService.createTask(taskDto));
    }

    @GetMapping("/{title}")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Ok", content =
                            { @Content(mediaType = "application/json", schema =
                            @Schema(implementation = Task.class)) }),
                    @ApiResponse(responseCode = "400", description = "Invalid title supplied"),
                    @ApiResponse(responseCode = "404", description = "Task not found"),
                    @ApiResponse(responseCode = "500", description = "Internal server error", content =
                            { @Content(mediaType = "application/json", schema =
                            @Schema(implementation = ErrorResponse.class)) }) }
    )
    public List<Task> findTask(@PathVariable("title") String title){
        return taskService.findTask(title);
    }

    @GetMapping()
    public  ResponseEntity<Page<TaskDto>>  findAllTask(
            @RequestParam(required = false) String title,
            @RequestParam(required = false) String status,
            @PageableDefault(sort = "dueDate", direction = Sort.Direction.ASC) Pageable pageable
    ){
        Page<TaskDto> tasks = taskService.findAllTask(title, status, pageable);
        return ResponseEntity.ok(tasks);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TaskDto>  updateTask(@PathVariable Long id, @RequestBody TaskDto taskDTO) throws Exception {
        TaskDto updatedTask = taskService.updateTask(id, taskDTO);
        return ResponseEntity.ok(updatedTask);

    }
    @Operation(
            summary = "Delete task",
            description = "Delete task by ID"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Task successfully deleted"),
            @ApiResponse(responseCode = "404", description = "Task not found")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable Long id){
        taskService.deleteTask(id);
        return ResponseEntity.noContent().build();
    }

}

