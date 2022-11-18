package com.intellias.intellistart.interviewplanning.exception;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Custom response from exceptions.
 */
@AllArgsConstructor
@Data
public class ExceptionResponse {

  private String errorCode;
  private String errorMessage;

}
