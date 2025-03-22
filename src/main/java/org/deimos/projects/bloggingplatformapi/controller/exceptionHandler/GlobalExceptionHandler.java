package org.deimos.projects.bloggingplatformapi.controller.exceptionHandler;

import io.netty.handler.codec.http.HttpResponseStatus;
import org.deimos.projects.bloggingplatformapi.exceptions.BlogPostNotFoundException;
import org.deimos.projects.bloggingplatformapi.model.Response;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * GlobalExceptionHandler is a centralized exception handling component for the application.
 * It intercepts exceptions thrown during the execution of controller methods and processes
 * them to provide a standardized error response structure.
 * <p>
 * This exception handler includes the following methods:
 * <p>
 * <ul>
 * <li> handleUnknownException: Handles generic exceptions that do not have a specific handler.
 *   Responds with a 500 Internal Server Error status code and a descriptive error message.
 * <li> handlePostNotFound: Handles BlogPostNotFoundException, which occurs when a requested blog
 *   post resource is not found. Responds with a 404 Not Found status code and a corresponding error message.
 * </ul>
 * Each method returns a Response object containing the appropriate HTTP status code and error message.
 * This approach ensures consistency in error handling across the application.
 */
@RestControllerAdvice
class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Response handleUnknownException(final Exception ex) {
        return new Response(HttpResponseStatus.INTERNAL_SERVER_ERROR.code(), ex.getMessage());
    }

    @ExceptionHandler(BlogPostNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Response handlePostNotFound(final Exception ex) {
        return new Response(HttpResponseStatus.NOT_FOUND.code(), ex.getMessage());
    }
}
