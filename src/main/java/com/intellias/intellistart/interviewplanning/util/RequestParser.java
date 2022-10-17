package com.intellias.intellistart.interviewplanning.util;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * Util class with methods, which parse User requests.
 */
public class RequestParser {

  public static String getUserEmailFromToken() {
    Authentication loggedInUser = SecurityContextHolder.getContext().getAuthentication();
    return loggedInUser.getName();
  }
}
