package com.guymontag.eventapi.exception;

import org.springframework.http.HttpStatus;

public class FuturEventCompletedException extends ApiException {
    public FuturEventCompletedException(String message) {
        super(message, HttpStatus.BAD_REQUEST);
    }

}
