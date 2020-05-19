package com.yongcheng.auth.exceptions;

@SuppressWarnings("serial")
public final class TokenInvalidException extends RuntimeException{
  
  public TokenInvalidException() {
    super();
  }

  public TokenInvalidException(final String message, final Throwable cause) {
    super(message, cause);
  }

  public TokenInvalidException(final String message) {
    super(message);
  }

  public TokenInvalidException(final Throwable cause) {
    super(cause);
  }  
}