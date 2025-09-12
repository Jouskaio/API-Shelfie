package com.shelfie.presentation.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * Global exception handler for REST controllers
 */
@RestControllerAdvice
public class GlobalExceptionHandler {
    
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> handleIllegalArgumentException(IllegalArgumentException ex) {
        ErrorResponse error = new ErrorResponse(
            "VALIDATION_ERROR",
            ex.getMessage(),
            LocalDateTime.now()
        );
        return ResponseEntity.badRequest().body(error);
    }
    
    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<ErrorResponse> handleIllegalStateException(IllegalStateException ex) {
        ErrorResponse error = new ErrorResponse(
            "BUSINESS_RULE_VIOLATION",
            ex.getMessage(),
            LocalDateTime.now()
        );
        return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
    }
    
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ValidationErrorResponse> handleValidationException(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        
        ValidationErrorResponse error = new ValidationErrorResponse(
            "VALIDATION_FAILED",
            "Request validation failed",
            LocalDateTime.now(),
            errors
        );
        return ResponseEntity.badRequest().body(error);
    }
    
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGenericException(Exception ex) {
        ErrorResponse error = new ErrorResponse(
            "INTERNAL_SERVER_ERROR",
            "An unexpected error occurred",
            LocalDateTime.now()
        );
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
    }
    
    /**
     * Standard error response
     */
    public static class ErrorResponse {
        private final String code;
        private final String message;
        private final LocalDateTime timestamp;
        
        public ErrorResponse(String code, String message, LocalDateTime timestamp) {
            this.code = code;
            this.message = message;
            this.timestamp = timestamp;
        }
        
        public String getCode() { return code; }
        public String getMessage() { return message; }
        public LocalDateTime getTimestamp() { return timestamp; }
    }
    
    /**
     * Validation error response with field details
     */
    public static class ValidationErrorResponse extends ErrorResponse {
        private final Map<String, String> fieldErrors;
        
        public ValidationErrorResponse(String code, String message, LocalDateTime timestamp, 
                                     Map<String, String> fieldErrors) {
            super(code, message, timestamp);
            this.fieldErrors = fieldErrors;
        }
        
        public Map<String, String> getFieldErrors() { return fieldErrors; }
    }
}