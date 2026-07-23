package com.guymontag.eventapi.exception;


import org.springframework.http.HttpStatus;

public class PastEventPlannedException extends ApiException {
    public PastEventPlannedException(String message) {
        super(message, HttpStatus.BAD_REQUEST);
    }
}
