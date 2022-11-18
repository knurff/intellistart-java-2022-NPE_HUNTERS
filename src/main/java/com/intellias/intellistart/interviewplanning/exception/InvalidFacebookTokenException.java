package com.intellias.intellistart.interviewplanning.exception;

/**
 * Exception for invalid Facebook token.
 */
public class InvalidFacebookTokenException extends RuntimeException {

  public InvalidFacebookTokenException(String message) {
    super(message);
  }
}
