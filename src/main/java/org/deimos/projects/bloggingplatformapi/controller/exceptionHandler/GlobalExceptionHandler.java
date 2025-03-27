package org.deimos.projects.bloggingplatformapi.controller.exceptionHandler;

import io.netty.handler.codec.http.HttpResponseStatus;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.deimos.projects.bloggingplatformapi.exceptions.BlogPostNotFoundException;
import org.deimos.projects.bloggingplatformapi.model.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * GlobalExceptionHandler is a centralized exception handling component for the application.
 * It intercepts exceptions thrown during the execution of controller methods and processes
 * them to provide a standardized error response structure.
 */
@RestControllerAdvice
class GlobalExceptionHandler {

    /**
     * Handles uncaught or unknown exceptions that occur during the execution of the application.
     * Provides a standardized error response with an HTTP 500 Internal Server Error status code
     * and a descriptive error message.
     *
     * @param ex the exception that was thrown and needs to be handled
     * @return a ErrorResponse object containing the HTTP 500 status code and the exception message
     */
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse handleUnknownException(final Exception ex) {
        return new ErrorResponse(HttpResponseStatus.INTERNAL_SERVER_ERROR.code(), ex.getMessage());
    }

    /**
     * Handles the {@code BlogPostNotFoundException} and returns a standardized error response
     * with an HTTP 404 Not Found status code and a descriptive error message.
     *
     * @param ex the exception that was thrown and needs to be handled
     * @return a {@code ErrorResponse} object containing the HTTP 404 status code and the exception message
     */
    @ExceptionHandler(BlogPostNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handlePostNotFound(final Exception ex) {
        return new ErrorResponse(HttpResponseStatus.NOT_FOUND.code(), ex.getMessage());
    }

    /**
     * Handles validation errors triggered by method argument constraints,
     * such as validation annotations on fields. It processes the validation
     * exception to extract error messages for each invalid field and returns
     * a structured response containing the field-specific error details.
     *
     * @param ex the exception thrown when method argument validation fails,
     *           containing details about the validation errors
     * @return a {@code ResponseEntity} containing an HTTP 400 status code
     *         and a map of field-specific error messages, where the key is
     *         the field name and the value is the corresponding error message
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationErrors(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();

        // Extract field-specific validation messages
        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            errors.put(error.getField(), error.getDefaultMessage());
        }

        // Return formatted error response
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
    }

    /**
     * Handles {@code ConstraintViolationException} exceptions that occur when constraint violations
     * are triggered, typically during validation processes. This method processes the violations
     * to extract relevant details and formats them into a structured error response.
     *
     * @param ex the {@code ConstraintViolationException} containing the details of the constraint violations
     * @return a {@code ResponseEntity} containing an HTTP 400 Bad Request status code
     *         and a map of errors where the key is the field name and the value is the corresponding error message
     */
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Map<String, String>> handleConstraintViolation(ConstraintViolationException ex) {
        Map<String, String> errors = new HashMap<>();

        // Extract and format constraint violations
        Set<ConstraintViolation<?>> violations = ex.getConstraintViolations();
        for (ConstraintViolation<?> violation : violations) {
            String field = violation.getPropertyPath().toString();
            String message = violation.getMessage();
            errors.put(field, message);
        }

        // Return formatted error response
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
    }
}
