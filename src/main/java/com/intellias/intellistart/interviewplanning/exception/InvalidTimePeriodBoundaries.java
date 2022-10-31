package com.intellias.intellistart.interviewplanning.exception;

/**
 * Exception for invalid time slot boundaries.
 */

public class InvalidTimePeriodBoundaries extends
    RuntimeException {

  public InvalidTimePeriodBoundaries(String message) {
    super(message);
  }
}
