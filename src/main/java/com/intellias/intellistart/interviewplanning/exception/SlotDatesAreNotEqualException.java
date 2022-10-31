package com.intellias.intellistart.interviewplanning.exception;

/**
 * Exception for not equal slot dates.
 */
public class SlotDatesAreNotEqualException extends RuntimeException {

  public SlotDatesAreNotEqualException(String message) {
    super(message);
  }
}
