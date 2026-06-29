package com.guymontag.eventapi.exception;

import org.springframework.http.HttpStatus;

public class NegativePageNumberException extends ApiException {
    public NegativePageNumberException(String message) {
        super(message, HttpStatus.BAD_REQUEST);
    }
}
