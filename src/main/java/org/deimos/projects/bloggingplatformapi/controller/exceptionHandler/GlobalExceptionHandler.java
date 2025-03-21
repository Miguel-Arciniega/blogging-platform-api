package org.deimos.projects.bloggingplatformapi.controller.exceptionHandler;

import io.netty.handler.codec.http.HttpResponseStatus;
import org.deimos.projects.bloggingplatformapi.exceptions.BlogPostNotFoundException;
import org.deimos.projects.bloggingplatformapi.model.Response;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

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
