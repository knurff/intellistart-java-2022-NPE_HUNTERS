package com.intellias.intellistart.interviewplanning.exception;

/**
 * Exception for changing not own slot.
 */
public class UserIsNotSlotOwnerException extends RuntimeException {

  public UserIsNotSlotOwnerException(String message) {
    super(message);
  }
}
