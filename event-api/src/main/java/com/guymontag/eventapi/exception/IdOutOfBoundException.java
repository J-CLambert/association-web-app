package com.guymontag.eventapi.exception;

import org.springframework.http.HttpStatus;

public class IdOutOfBoundException extends ApiException {
    public IdOutOfBoundException(String message) {
        super(message, HttpStatus.BAD_REQUEST);
    }
}
