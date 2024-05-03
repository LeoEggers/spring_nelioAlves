package com.nelio.spring.course.resources.exceptions;

import com.nelio.spring.course.services.exceptions.DatabaseException;
import com.nelio.spring.course.services.exceptions.ResourceNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.Instant;

@ControllerAdvice
public class ResourceExceptionHandler {

    @ExceptionHandler(Exception.class) // Pode ser Exception para capturar todas as exceções
    public ResponseEntity<StandardError> handleException(Exception e, HttpServletRequest request) {

        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        String errorMessage = "Internal server error";

        if (e instanceof ResourceNotFoundException) {
            status = HttpStatus.NOT_FOUND;
            errorMessage = "Resource not found";
        } else if (e instanceof DatabaseException) {
            status = HttpStatus.BAD_REQUEST;
            errorMessage = "Database integrity violation";
        }

        StandardError error = new StandardError(Instant.now(),
                status.value(),
                errorMessage,
                e.getMessage(),
                request.getRequestURI());

        return ResponseEntity.status(status).body(error);
    }
}
