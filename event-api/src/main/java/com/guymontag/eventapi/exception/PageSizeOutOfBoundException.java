package com.guymontag.eventapi.exception;

public class PageSizeOutOfBoundException extends RuntimeException {
  public PageSizeOutOfBoundException(String message) {
    super(message);
  }
}
