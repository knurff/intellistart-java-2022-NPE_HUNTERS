package com.intellias.intellistart.interviewplanning.exception;

/**
 * Exception for conditions, when given identifier does not belong to user.
 */
public class IdentifierDoesNotBelongToUserException extends RuntimeException {

  public IdentifierDoesNotBelongToUserException(String message) {
    super(message);
  }
}
