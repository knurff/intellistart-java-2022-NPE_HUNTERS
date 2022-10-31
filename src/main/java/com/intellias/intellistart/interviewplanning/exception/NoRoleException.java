package com.intellias.intellistart.interviewplanning.exception;

/**
 * Exception to handle removal role from user, who doesn't have role.
 */
public class NoRoleException extends RuntimeException {

  public NoRoleException(String message) {
    super(message);
  }
}
