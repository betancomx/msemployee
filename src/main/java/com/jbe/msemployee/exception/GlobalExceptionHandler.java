package com.jbe.msemployee.exception;

import static com.jbe.msemployee.commons.Constants.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.http.converter.HttpMessageNotReadableException;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import jakarta.validation.ConstraintViolationException;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(EmployeeNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleNotFound(EmployeeNotFoundException ex) {
        Map<String, String> response = new HashMap<>();
        response.put(RESPONSE_ERROR_KEY, ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidations(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error ->
                errors.put(error.getField(), error.getDefaultMessage()));
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

    // Handler general para cualquier otro
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, String>> handleGlobalException(Exception ex) {
        Map<String, String> response = new HashMap<>();
        response.put(RESPONSE_ERROR_KEY, MSG_INTERNAL_SERVER_ERROR);
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Map<String, String>> handleConstraintViolations(ConstraintViolationException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getConstraintViolations().forEach(error -> {
            // Extrae el nombre del campo que falló y su mensaje
            String path = error.getPropertyPath().toString();
            errors.put(path, error.getMessage());}
        );
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Map<String, String>> handleHttpMessageNotReadable(HttpMessageNotReadableException ex) {
        Map<String, String> errors = new HashMap<>();
        if (ex.getCause() instanceof InvalidFormatException invalidFormatException) {
            invalidFormatException.getPath().forEach(reference -> {
                String fieldName = reference.getFieldName();
                if (fieldName != null) {
                    errors.put(fieldName, MSG_INVALID_ENUM_VALUE);
                }
            });
            // Si por alguna razón no extrajo ningún campo, mandamos el genérico
            if (errors.isEmpty()) {
                errors.put("error", MSG_INVALID_JSON_FORMAT);
            }
        } else {
            errors.put("error", MSG_INVALID_JSON_FORMAT);
        }
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }
}