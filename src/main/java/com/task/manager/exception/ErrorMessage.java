package com.task.manager.exception;

import org.springframework.http.HttpStatus;

public enum ErrorMessage {
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "User not found"),
    EMAIL_ALREADY_EXISTS(HttpStatus.CONFLICT, "Email is already registered"),
    TASK_NOT_FOUND(HttpStatus.NOT_FOUND, "Task not found"),
    TASK_ALREADY_EXISTS(HttpStatus.CONFLICT, "Task already exists"),
    INVALID_TASK_STATUS(HttpStatus.BAD_REQUEST, "Invalid task status"),

    INVALID_CREDENTIALS(HttpStatus.UNAUTHORIZED, "Invalid email or password"),
    TOKEN_EXPIRED(HttpStatus.UNAUTHORIZED, "JWT token has expired"),
    INVALID_TOKEN(HttpStatus.UNAUTHORIZED, "Invalid or expired token"),

    INTERNAL_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "Unexpected error occurred"),
    BAD_REQUEST(HttpStatus.BAD_REQUEST, "Bad request"),
    VALIDATION_ERROR(HttpStatus.BAD_REQUEST, "Validation failed");



    private final HttpStatus status;
    private final String message;

    ErrorMessage(HttpStatus status, String message) {
        this.status = status;
        this.message = message;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

}
