package com.guymontag.eventapi.controller;

import com.guymontag.eventapi.exception.ApiException;
import com.guymontag.eventapi.dto.response.EventErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionHandlerEvent {


    //handler for custom Exception
    @ExceptionHandler
    public ResponseEntity<EventErrorResponse> errorResponseResponseEntity(ApiException apiException) {

        EventErrorResponse eventErrorResponse = new EventErrorResponse();

        eventErrorResponse.setMessage(apiException.getMessage());

        eventErrorResponse.setHttpStatus(apiException.getHttpStatus());

        eventErrorResponse.setTimestamp(System.currentTimeMillis());

        return new ResponseEntity<>(eventErrorResponse, eventErrorResponse.getHttpStatus());
    }

    //handler for any other exception
    @ExceptionHandler
    public ResponseEntity<EventErrorResponse> errorResponseResponseEntity(Exception exception) {

        EventErrorResponse eventErrorResponse = new EventErrorResponse();

        eventErrorResponse.setMessage(eventErrorResponse.getMessage());

        eventErrorResponse.setTimestamp(System.currentTimeMillis());

        eventErrorResponse.setHttpStatus(HttpStatus.BAD_REQUEST);

        return new ResponseEntity<>(eventErrorResponse, HttpStatus.BAD_REQUEST);
    }
}
