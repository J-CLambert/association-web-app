package com.guymontag.eventapi.exception;

import org.springframework.http.HttpStatus;

public class EventNotFoundException extends ApiException {
    public EventNotFoundException(String message) {
        super(message, HttpStatus.NOT_FOUND);
    }
}
