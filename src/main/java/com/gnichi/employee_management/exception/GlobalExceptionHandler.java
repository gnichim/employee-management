package com.gnichi.employee_management.exception;

import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@RestControllerAdvice
public class GlobalExceptionHandler {

    /* Spring Boot’s @RestControllerAdvice mechanism intercepts this exception, calls your handleEntityNotFound() method,
     * and returns the formatted JSON error response without you writing any try/catch in the controller. (The same applies for IllegalArgumentException)*/
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ApiErrorResponse> handleEntityNotFound( // Will be triggered whenever an EntityNotFoundException is thrown
        EntityNotFoundException ex,
        HttpServletRequest request
    ) {
        ApiErrorResponse errorResponse = new ApiErrorResponse(
          HttpStatus.NOT_FOUND,
          ex.getMessage(),
          request.getRequestURI()
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ApiErrorResponse> handleIllegalArgument( // Will be triggered whenever an IllegalArgumentException is thrown
            IllegalArgumentException ex,
            HttpServletRequest request
    ) {
        ApiErrorResponse errorResponse = new ApiErrorResponse(
                HttpStatus.CONFLICT,
                ex.getMessage(),
                request.getRequestURI()
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.CONFLICT);
    }
}
