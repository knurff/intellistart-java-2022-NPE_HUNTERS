package com.intellias.intellistart.interviewplanning.exception;

/**
 * Exception to handle giving role to user with already existing role.
 */
public class UserAlreadyHasRoleException extends RuntimeException {

  public UserAlreadyHasRoleException(String message) {
    super(message);
  }
}