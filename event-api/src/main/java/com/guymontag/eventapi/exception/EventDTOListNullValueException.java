package com.guymontag.eventapi.exception;

import org.springframework.http.HttpStatus;

public class EventDTOListNullValueException extends ApiException {
    public EventDTOListNullValueException(String message) {
        super(message, HttpStatus.BAD_REQUEST);
    }
}
