package com.guymontag.eventapi.exception;

public class NegativePageNumberException extends RuntimeException {
    public NegativePageNumberException(String message) {
        super(message);
    }
}
