package com.yongcheng.auth.handlers;

import com.yongcheng.auth.exceptions.UserAlreadyExistException;
import com.yongcheng.auth.payloads.ApiResponse;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {
  
  public RestResponseEntityExceptionHandler() {
    super();
  }

  @ExceptionHandler({ UserAlreadyExistException.class })
  public ResponseEntity<Object> handleBadRequest(final RuntimeException ex, final WebRequest request) {
    final String     body   = ex.getMessage();
    final HttpStatus status = HttpStatus.BAD_REQUEST;

    return new ResponseEntity<Object>(new ApiResponse(body, ex), status);
  }

  @ExceptionHandler({ RuntimeException.class })
  public ResponseEntity<Object> handleInternalServerError(final RuntimeException ex, final WebRequest request) {
    final String       body = ex.getMessage();
    final HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;

    return new ResponseEntity<Object>(new ApiResponse(body, ex), status);
  }
}