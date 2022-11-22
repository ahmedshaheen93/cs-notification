package com.shaheen.csnotification.exception;

import org.springframework.http.HttpStatus;

public class GeneralServerError extends ApiException {
  public GeneralServerError(String message) {
    super(message);
  }

  @Override
  protected HttpStatus getHttpStatus() {
    return HttpStatus.INTERNAL_SERVER_ERROR;
  }
}
