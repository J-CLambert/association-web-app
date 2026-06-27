package com.guymontag.eventapi.exception;

public class EventPageNumberSmallerThanZeroException extends RuntimeException {
    public EventPageNumberSmallerThanZeroException(String message) {
        super(message);
    }
}
