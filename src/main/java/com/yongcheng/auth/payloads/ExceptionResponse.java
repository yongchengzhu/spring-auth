package com.yongcheng.auth.payloads;

public class ExceptionResponse {
  
  private String error;

  private String exception;

  private String message;

  public ExceptionResponse() {}

  public ExceptionResponse(String error, String exception, String message) {
    this.error = error;
    this.exception = exception;
    this.message = message;
  }

  public String getError() {
    return error;
  }

  public void setError(String error) {
    this.error = error;
  }

  public String getException() {
    return exception;
  }

  public void setException(String exception) {
    this.exception = exception;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  };
}