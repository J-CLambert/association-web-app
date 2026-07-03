package com.guymontag.eventapi.exception;

import org.springframework.http.HttpStatus;

import java.util.Objects;

public abstract class ApiException extends RuntimeException {

    private final HttpStatus httpStatus;

    public ApiException(String message, HttpStatus httpStatus) {
        super(message);
        this.httpStatus = Objects.requireNonNull(httpStatus, "message si null");
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }
}
