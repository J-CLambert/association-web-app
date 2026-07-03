package com.guymontag.eventapi.controller;

import com.guymontag.eventapi.exception.ApiException;
import com.guymontag.eventapi.view.response.EventErrorResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionHandlerEvent {

    private static final Logger log = LoggerFactory.getLogger(ExceptionHandlerEvent.class);

    //handler for custom Exception
    @ExceptionHandler
    public ResponseEntity<EventErrorResponse> errorResponseResponseEntity(ApiException apiException) {

        EventErrorResponse eventErrorResponse = new EventErrorResponse();

        eventErrorResponse.setMessage(apiException.getMessage());

        eventErrorResponse.setHttpStatus(apiException.getHttpStatus());

        eventErrorResponse.setTimestamp(System.currentTimeMillis());

        chooseWhichLoggingLevel(apiException);

        return new ResponseEntity<>(eventErrorResponse, eventErrorResponse.getHttpStatus());
    }

    //handler for any other exception
    @ExceptionHandler
    public ResponseEntity<EventErrorResponse> errorResponseResponseEntity(Exception exception) {

        EventErrorResponse eventErrorResponse = new EventErrorResponse();

        eventErrorResponse.setMessage(exception.getMessage());

        eventErrorResponse.setTimestamp(System.currentTimeMillis());

        eventErrorResponse.setHttpStatus(HttpStatus.INTERNAL_SERVER_ERROR);

        log.error("error message :{}, error trace :{}",exception.getMessage(),exception);

        return new ResponseEntity<>(eventErrorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private void chooseWhichLoggingLevel(ApiException ex) {
        if (ex.getHttpStatus().is4xxClientError()) {
            log.warn("{}",ex.getHttpStatus().name());
        } else if (ex.getHttpStatus().is5xxServerError()) {
            log.error("error : {}",ex);
        } else {
            log.error("{}",ex.getHttpStatus().name());
        }
    }
}
