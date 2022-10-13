package com.intellias.intellistart.interviewplanning.exception;

/**
 * Exception for non-existent interviewer.
 */

public class InterviewerNotFoundException extends
    RuntimeException {

  public InterviewerNotFoundException(String message) {
    super(message);
  }
}
