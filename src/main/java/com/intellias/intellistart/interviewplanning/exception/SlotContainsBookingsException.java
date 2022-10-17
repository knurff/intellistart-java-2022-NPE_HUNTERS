package com.intellias.intellistart.interviewplanning.exception;

/**
 * Exception for changing slot with bookings.
 */

public class SlotContainsBookingsException extends RuntimeException {

  public SlotContainsBookingsException(String message) {
    super(message);
  }
}
