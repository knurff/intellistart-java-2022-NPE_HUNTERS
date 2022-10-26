package com.intellias.intellistart.interviewplanning.exception;

/**
 * Exception to handle self-revoking for coordinator.
 */
public class SelfRevokingException extends RuntimeException {

  public SelfRevokingException(String message) {
    super(message);
  }
}
