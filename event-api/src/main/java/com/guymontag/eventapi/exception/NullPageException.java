package com.guymontag.eventapi.exception;

import org.springframework.http.HttpStatus;

public class NullPageException extends ApiException {
    public NullPageException(String message) {
        super(message, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
