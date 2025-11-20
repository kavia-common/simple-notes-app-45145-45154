package com.example.notesbackend.exception;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.ErrorResponseException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.OffsetDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

/**
 * Global exception handler for consistent API error responses.
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    @Schema(name = "ErrorResponse", description = "Standard error response")
    static class ErrorPayload {
        public String message;
        public int status;
        public String error;
        public OffsetDateTime timestamp = OffsetDateTime.now();
        public Map<String, String> details;

        static ErrorPayload of(HttpStatus status, String message) {
            ErrorPayload p = new ErrorPayload();
            p.status = status.value();
            p.error = status.getReasonPhrase();
            p.message = message;
            return p;
        }
    }

    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<Object> handleNotFound(NoSuchElementException ex) {
        ErrorPayload payload = ErrorPayload.of(HttpStatus.NOT_FOUND, ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(payload);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Object> handleConstraintViolation(ConstraintViolationException ex) {
        ErrorPayload payload = ErrorPayload.of(HttpStatus.BAD_REQUEST, "Validation failed");
        Map<String, String> details = new HashMap<>();
        ex.getConstraintViolations().forEach(v -> details.put(v.getPropertyPath().toString(), v.getMessage()));
        payload.details = details;
        return ResponseEntity.badRequest().body(payload);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex) {
        ErrorPayload payload = ErrorPayload.of(HttpStatus.BAD_REQUEST, "Validation failed");
        Map<String, String> details = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(err -> details.put(err.getField(), err.getDefaultMessage()));
        payload.details = details;
        return ResponseEntity.badRequest().body(payload);
    }

    @ExceptionHandler(BindException.class)
    public ResponseEntity<Object> handleBindException(BindException ex) {
        ErrorPayload payload = ErrorPayload.of(HttpStatus.BAD_REQUEST, "Validation failed");
        Map<String, String> details = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(err -> details.put(err.getField(), err.getDefaultMessage()));
        payload.details = details;
        return ResponseEntity.badRequest().body(payload);
    }

    @ExceptionHandler(ErrorResponseException.class)
    public ResponseEntity<Object> handleErrorResponse(ErrorResponseException ex) {
        HttpStatus httpStatus = HttpStatus.valueOf(ex.getStatusCode().value());
        ErrorPayload payload = ErrorPayload.of(httpStatus, ex.getMessage());
        return ResponseEntity.status(httpStatus).body(payload);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleGeneric(Exception ex) {
        ErrorPayload payload = ErrorPayload.of(HttpStatus.INTERNAL_SERVER_ERROR, "Unexpected error");
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(payload);
    }
}
