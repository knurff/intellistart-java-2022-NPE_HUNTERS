package com.intellias.intellistart.interviewplanning.exception;

import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

/**
 * Handles requests from users, that was not authenticated and returns response with 403 response
 * status.
 */
@Component("customAuthenticationEntryPoint")
@AllArgsConstructor
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {
  private final UnauthorizedExceptionHandler unauthorizedExceptionHandler;

  @Override
  public void commence(HttpServletRequest request, HttpServletResponse response,
      AuthenticationException authException) throws IOException {
    unauthorizedExceptionHandler.handleUnauthorizedException(response);
  }
}
