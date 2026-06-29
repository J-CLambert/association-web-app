package com.guymontag.eventapi.exception;

import org.springframework.http.HttpStatus;

public class MaxSizeException extends ApiException {
    public MaxSizeException(String message) {
        super(message, HttpStatus.BAD_REQUEST);
    }
}
