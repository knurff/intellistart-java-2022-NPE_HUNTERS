package com.intellias.intellistart.interviewplanning.exception;

/**
 * Exception for invalid day of week.
 */

public class InvalidDayOfWeekException extends
    RuntimeException {

  public InvalidDayOfWeekException(String message) {
    super(message);
  }
}
