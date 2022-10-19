package com.intellias.intellistart.interviewplanning.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;


/**
 * Controller for handling exception in application.
 */
@ControllerAdvice
public class ApplicationExceptionHandler {

  private static final String INTERVIEWER_NOT_FOUND = "interviewer_not_found";
  private static final String SLOT_IS_OVERLAPPING = "slot_is_overlapping";
  private static final String INVALID_BOUNDARIES = "invalid_boundaries";
  private static final String INVALID_DAY_OF_WEEK = "invalid_day_of_week";
  private static final String SLOT_NOT_FOUND = "slot_not_found";
  private static final String SLOT_ALREADY_HAS_BOOKINGS = "slot_already_has_bookings";

  private static final String INVALID_SLOT_DATE = "invalid_slot_date";

  private static final String BOOKING_NOT_FOUND = "booking_not_found";

  /**
   * Exception handler for InterviewerNotFoundException.
   */

  @ExceptionHandler
  public ResponseEntity<ExceptionResponse> handleInterviewerNotFoundException(
      InterviewerNotFoundException e) {
    return new ResponseEntity<>(new ExceptionResponse(INTERVIEWER_NOT_FOUND, e.getMessage()),
        HttpStatus.NOT_FOUND);
  }

  /**
   * Exception handler for SlotNotFoundException.
   */
  @ExceptionHandler
  public ResponseEntity<ExceptionResponse> handleSlotNotFoundException(SlotNotFoundException e) {
    return new ResponseEntity<>(new ExceptionResponse(SLOT_NOT_FOUND, e.getMessage()),
        HttpStatus.NOT_FOUND);
  }

  /**
   * Exception handler for SlotIsOverlappingException.
   */
  @ExceptionHandler
  public ResponseEntity<ExceptionResponse> handleSlotIsOverlappingException(
      SlotIsOverlappingException e) {
    return new ResponseEntity<>(new ExceptionResponse(SLOT_IS_OVERLAPPING, e.getMessage()),
        HttpStatus.BAD_REQUEST);
  }

  /**
   * Exception handler for InvalidTimeSlotBoundariesException.
   */
  @ExceptionHandler
  public ResponseEntity<ExceptionResponse> handleInvalidTimeSlotBoundariesException(
      InvalidTimeSlotBoundariesException e) {
    return new ResponseEntity<>(new ExceptionResponse(INVALID_BOUNDARIES, e.getMessage()),
        HttpStatus.BAD_REQUEST);
  }

  /**
   * Exception handler for InvalidDayOfWeekException.
   */
  @ExceptionHandler
  public ResponseEntity<ExceptionResponse> handleInvalidDayOfWeekException(
      InvalidDayOfWeekException e) {
    return new ResponseEntity<>(new ExceptionResponse(INVALID_DAY_OF_WEEK, e.getMessage()),
        HttpStatus.BAD_REQUEST);
  }

  /**
   * Exception handler for SlotContainsBookingsException.
   */
  @ExceptionHandler
  public ResponseEntity<ExceptionResponse> handleSlotAlreadyHasBookingsException(
      SlotContainsBookingsException e) {
    return new ResponseEntity<>(new ExceptionResponse(SLOT_ALREADY_HAS_BOOKINGS, e.getMessage()),
        HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler
  public ResponseEntity<ExceptionResponse> handleInvalidCandidateSlotDateException(
      InvalidCandidateSlotDateException e) {
    return new ResponseEntity<>(new ExceptionResponse(INVALID_SLOT_DATE, e.getMessage()),
        HttpStatus.BAD_REQUEST);
  }

  /**
   * Exception handler for BookingNotFoundException.
   */

  @ExceptionHandler
  public ResponseEntity<ExceptionResponse> handleBookingNotFoundException(
      BookingNotFoundException e) {
    return new ResponseEntity<>(new ExceptionResponse(BOOKING_NOT_FOUND, e.getMessage()),
        HttpStatus.NOT_FOUND);
  }

  /**
   * Custom response from exceptions.
   */
  @AllArgsConstructor
  @Data
  public static class ExceptionResponse {

    private String errorCode;
    private String errorMessage;

  }

}
