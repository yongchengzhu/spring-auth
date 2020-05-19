package com.yongcheng.auth.exceptions;

@SuppressWarnings("serial")
public final class TokenNotFoundException extends RuntimeException {
  
  public TokenNotFoundException() {
    super();
  }

  public TokenNotFoundException(final String message, final Throwable cause) {
    super(message, cause);
  }

  public TokenNotFoundException(final String message) {
    super(message);
  }

  public TokenNotFoundException(final Throwable cause) {
    super(cause);
  }
}