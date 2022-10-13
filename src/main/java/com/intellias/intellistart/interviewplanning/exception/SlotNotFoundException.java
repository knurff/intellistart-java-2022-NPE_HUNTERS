package com.intellias.intellistart.interviewplanning.exception;

/**
 * Exception for non-existing slot.
 */

public class SlotNotFoundException extends
    RuntimeException {

  public SlotNotFoundException(String message) {
    super(message);
  }
}
