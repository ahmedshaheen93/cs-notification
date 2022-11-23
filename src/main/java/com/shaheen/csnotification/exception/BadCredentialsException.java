package com.shaheen.csnotification.exception;

import org.springframework.http.HttpStatus;

public class BadCredentialsException extends ApiException {
  public BadCredentialsException(String message) {
    super(message);
  }

  @Override
  protected HttpStatus getHttpStatus() {
    return HttpStatus.UNAUTHORIZED;
  }
}
