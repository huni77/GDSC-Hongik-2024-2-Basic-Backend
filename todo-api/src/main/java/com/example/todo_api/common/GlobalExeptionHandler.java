package com.example.todo_api.common;

import org.springframework.http.ResponseEntity;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExeptionHandler {
    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<ErrorResponse> handleUnknownException(Exception ex) {
        ErrorResponse errorResponse = (ErrorResponse) new com.example.todo_api.common.dto.ErrorResponse(ex.getMessage());
        return ResponseEntity.internalServerError().body(errorResponse);
    }
}
