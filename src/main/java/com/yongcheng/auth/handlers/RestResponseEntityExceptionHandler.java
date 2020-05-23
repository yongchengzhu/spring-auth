package com.yongcheng.auth.handlers;

import com.yongcheng.auth.exceptions.TokenInvalidException;
import com.yongcheng.auth.exceptions.TokenNotFoundException;
import com.yongcheng.auth.exceptions.UserAlreadyExistException;
import com.yongcheng.auth.payloads.ExceptionResponse;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {
  
  public RestResponseEntityExceptionHandler() {
    super();
  }

  @ExceptionHandler({ UserAlreadyExistException.class })
  public ResponseEntity<?> handleBadRequest(final RuntimeException ex) {
    final String error      = "Bad Request";
    final String exception  = "UserAlreadyExistException";
    final String message    = ex.getMessage();
    final HttpStatus status = HttpStatus.BAD_REQUEST;
    
    return new ResponseEntity<>(new ExceptionResponse(error, exception, message), status);
  }

  @ExceptionHandler({ RuntimeException.class })
  public ResponseEntity<?> handleInternalServerError(final RuntimeException ex) {
    final String error      = "Internal Server Error";
    final String exception  = "RuntimeException";
    final String message    = ex.getMessage();
    final HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;

    return new ResponseEntity<>(new ExceptionResponse(error, exception, message), status);
  }

  @ExceptionHandler({ TokenNotFoundException.class })
  public ResponseEntity<?> handleNotFound(final RuntimeException ex) {
    final String error      = "Not Found";
    final String exception  = "TokenNotFoundException";
    final String message    = ex.getMessage();
    final HttpStatus status = HttpStatus.NOT_FOUND;

    return new ResponseEntity<>(new ExceptionResponse(error, exception, message), status);    
  }

  @ExceptionHandler({ TokenInvalidException.class})
  public ResponseEntity<?> handleInvalidToken(final RuntimeException ex) {
    final String error      = "Invalid Token";
    final String exception  = "TokenInvalidException";
    final String message    = ex.getMessage();
    final HttpStatus status = HttpStatus.UNAUTHORIZED;

    return new ResponseEntity<>(new ExceptionResponse(error, exception, message), status);  
  }
}