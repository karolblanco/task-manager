package com.task.manager.exception;

import com.task.manager.payload.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(ApiException.class)
    public ResponseEntity<ErrorResponse> handleApiException(ApiException ex) {
        ErrorMessage error = ex.getErrorMessage();

        ErrorResponse response = new ErrorResponse(
                error.getStatus().value(),
                error.name(),
                error.getMessage()
        );

        return new ResponseEntity<>(response, error.getStatus());
    }


    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationException(MethodArgumentNotValidException ex) {
        String message = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .findFirst()
                .orElse("Validation error");

        ErrorResponse response = new ErrorResponse(
                HttpStatus.BAD_REQUEST.value(),
                ErrorMessage.VALIDATION_ERROR.name(),
                message
        );

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGeneralException(Exception ex) {
        ErrorMessage error = ErrorMessage.INTERNAL_ERROR;

        ErrorResponse response = new ErrorResponse(
                error.getStatus().value(),
                error.name(),
                error.getMessage()
        );

        return new ResponseEntity<>(response, error.getStatus());
    }
}
