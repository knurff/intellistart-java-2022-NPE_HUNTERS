package com.intellias.intellistart.interviewplanning.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * Util class with methods, which parse users requests.
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class RequestParser {

  public static String getUserEmailFromToken() {
    return getAuthenticationFromSecurityContext().getName();
  }

  public static String getUserRoleFromToken() {
    return getAuthenticationFromSecurityContext().getAuthorities().stream().findFirst().get()
        .toString();
  }

  private static Authentication getAuthenticationFromSecurityContext() {
    return SecurityContextHolder.getContext().getAuthentication();
  }
}
