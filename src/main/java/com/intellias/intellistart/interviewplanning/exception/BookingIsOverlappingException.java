package com.intellias.intellistart.interviewplanning.exception;

/**
 * Exception for booking overlapping.
 */
public class BookingIsOverlappingException extends RuntimeException {

  public BookingIsOverlappingException(String message) {
    super(message);
  }
}
