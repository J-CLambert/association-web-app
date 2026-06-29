package com.guymontag.eventapi.view.response;

import jdk.jfr.StackTrace;
import org.springframework.http.HttpStatus;

import java.security.Timestamp;

public class ErrorResponse {

    private String message;
    private HttpStatus httpStatus;
    private Timestamp timestamp;

    public ErrorResponse(String message, HttpStatus httpStatus, Timestamp timestamp) {
        this.message = message;
        this.httpStatus = httpStatus;
        this.timestamp = timestamp;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public void setHttpStatus(HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }
}
