package com.intellias.intellistart.interviewplanning.exception;


/**
 * Exception for non-existent booking.
 */
public class BookingNotFoundException extends RuntimeException {

  public BookingNotFoundException(String message) {
    super(message);
  }

}