package com.yongcheng.auth.payloads;

public class ApiResponse {
  private String message;
  private RuntimeException error;

  public ApiResponse() {

  }

  public ApiResponse(String message) {
    this.message = message;
    this.error = null;
  }

  public ApiResponse(String message, RuntimeException error) {
    this.message = message;
    this.error = error;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public RuntimeException getError() {
    return error;
  }

  public void setError(RuntimeException error) {
    this.error = error;
  }
}