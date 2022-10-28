package com.intellias.intellistart.interviewplanning.exception;

/**
 * Exception for exceeding of interviewer booking limit.
 */
public class BookingLimitExceededException extends RuntimeException {

  public BookingLimitExceededException(String message) {
    super(message);
  }
}
