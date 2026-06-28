package com.guymontag.eventapi.exception;

public class MaxSizeException extends RuntimeException {
    public MaxSizeException(String message) {
        super(message);
    }
}
