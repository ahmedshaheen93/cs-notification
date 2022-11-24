package com.shaheen.csnotification.exception;

import org.springframework.http.HttpStatus;

public class NotificationException extends ApiException {
  private final HttpStatus httpStatus;

  public NotificationException(String message, HttpStatus httpStatus) {
    super(message);
    this.httpStatus = httpStatus;
  }

  @Override
  protected HttpStatus getHttpStatus() {
    return this.httpStatus;
  }
}
