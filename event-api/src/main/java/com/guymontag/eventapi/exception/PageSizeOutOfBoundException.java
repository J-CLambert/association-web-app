package com.guymontag.eventapi.exception;

import org.springframework.http.HttpStatus;

public class PageSizeOutOfBoundException extends ApiException {
    public PageSizeOutOfBoundException(String message) {
        super(message, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
