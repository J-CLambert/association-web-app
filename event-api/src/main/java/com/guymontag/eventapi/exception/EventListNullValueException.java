package com.guymontag.eventapi.exception;

import org.springframework.http.HttpStatus;

public class EventListNullValueException extends ApiException {
    public EventListNullValueException(String message) {
        super(message, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
