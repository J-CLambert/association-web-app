package com.guymontag.eventapi.exception;

import org.springframework.http.HttpStatus;

public class IdValueNullException extends ApiException {
    public IdValueNullException(String message) {
        super(message, HttpStatus.BAD_REQUEST);
    }
}
