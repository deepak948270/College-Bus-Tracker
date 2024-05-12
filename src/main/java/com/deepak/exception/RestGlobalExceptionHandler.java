package com.deepak.exception;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.nio.file.AccessDeniedException;
import java.util.Map;

@RestControllerAdvice
public class RestGlobalExceptionHandler {
    
    @ExceptionHandler(DataIntegrityViolationException.class)
    @ResponseBody
    public ResponseEntity<String> handleDataIntegrityViolation(DataIntegrityViolationException e) {
        // Handle the unique constraint violation error
        // You can log the error or return a meaningful message to the user
        // For example:
        return ResponseEntity.status(HttpStatus.CONFLICT).body("Username already exists");
    }




    @ExceptionHandler(value = AccessDeniedException.class)
    public ResponseEntity<Map<String, String>> accessDeniedEx(AccessDeniedException exception) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(Map.of("error message ", exception.getMessage()));
    }

    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<Map<String, String>> generalEx(Exception exception) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(Map.of("error message ", exception.getMessage()));
    }
}
