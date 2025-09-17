package com.task.manager.controller;

import com.task.manager.controller.dto.TaskDto;
import com.task.manager.entity.Task;
import com.task.manager.security.JwtUtils;
import com.task.manager.service.TaskService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.servlet.http.HttpServletRequest;
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

import static com.task.manager.security.HttpRequestUtils.extractToken;

@RestController
@RequestMapping("/tasks")
public class TaskController {
    private final TaskService taskService;
    private final JwtUtils jwtUtil;

    public TaskController(TaskService taskService, JwtUtils jwtUtil) {
        this.taskService = taskService;
        this.jwtUtil = jwtUtil;
    }
    private Long getUserIdFromToken(HttpServletRequest request) {
        String token = extractToken(request);
        return jwtUtil.extractUserId(token);
    }
    @Operation(
            summary = "Create task"
    )
    @PostMapping()
    public ResponseEntity<TaskDto> createTask(@Valid @RequestBody TaskDto taskDto, HttpServletRequest request) {
       Long userId = getUserIdFromToken(request);
        return  ResponseEntity.status(HttpStatus.CREATED).body(taskService.createTask(taskDto, userId));
    }

    @GetMapping("title/{title}")
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
    public TaskDto findTaskByTitle(@PathVariable("title") String title, HttpServletRequest request){
        Long userId = getUserIdFromToken(request);
        return taskService.getTaskByTitle(title, userId);
    }

    @GetMapping("/{id}")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Ok", content =
                            { @Content(mediaType = "application/json", schema =
                            @Schema(implementation = Task.class)) }),
                    @ApiResponse(responseCode = "400", description = "Invalid id supplied"),
                    @ApiResponse(responseCode = "404", description = "Task not found"),
                    @ApiResponse(responseCode = "500", description = "Internal server error", content =
                            { @Content(mediaType = "application/json", schema =
                            @Schema(implementation = ErrorResponse.class)) }) }
    )
    public TaskDto findTaskById(@PathVariable("id") Long id, HttpServletRequest request){
        Long userId = getUserIdFromToken(request);
        return taskService.getTaskById(id, userId);
    }

    @GetMapping()
    public  ResponseEntity<Page<TaskDto>>  findAllTask(
            @RequestParam(required = false) String title,
            @RequestParam(required = false) String status,
            @PageableDefault(sort = "dueDate", direction = Sort.Direction.ASC) Pageable pageable,
            HttpServletRequest request
    ){
        Long userId = getUserIdFromToken(request);
        Page<TaskDto> tasks = taskService.findAllTask(title, status, pageable, userId);
        return ResponseEntity.ok(tasks);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TaskDto>  updateTask(@PathVariable Long id, @RequestBody TaskDto taskDTO, HttpServletRequest request) {
        Long userId = getUserIdFromToken(request);
        TaskDto updatedTask = taskService.updateTask(id, taskDTO, userId);
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
    public ResponseEntity<Void> deleteTask(@PathVariable Long id, HttpServletRequest request){
        Long userId = getUserIdFromToken(request);
        taskService.deleteTask(id, userId);
        return ResponseEntity.noContent().build();
    }

}

