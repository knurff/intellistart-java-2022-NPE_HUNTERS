package com.intellias.intellistart.interviewplanning.exception;

/**
 * Exception for slot overlapping.
 */

public class SlotIsOverlappingException extends
    RuntimeException {

  public SlotIsOverlappingException(String message) {
    super(message);
  }
}
