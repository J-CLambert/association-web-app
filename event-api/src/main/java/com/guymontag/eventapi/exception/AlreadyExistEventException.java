package com.guymontag.eventapi.exception;

import org.springframework.http.HttpStatus;

public class AlreadyExistEventException extends ApiException {
    public AlreadyExistEventException(String message) {
        super(message, HttpStatus.CONFLICT);
    }
}
