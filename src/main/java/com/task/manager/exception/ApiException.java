package com.task.manager.exception;

public class ApiException extends RuntimeException {
    private final ErrorMessage errorMessage;

    public ApiException(ErrorMessage errorMessage) {
        super(errorMessage.getMessage());
        this.errorMessage = errorMessage;
    }

    public ErrorMessage getErrorMessage() {
        return errorMessage;
    }
}
