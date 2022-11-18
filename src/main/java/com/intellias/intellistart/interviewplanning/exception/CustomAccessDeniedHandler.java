package com.intellias.intellistart.interviewplanning.exception;

import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

/**
 * Handles requests, that was denied by security reasons and returns response with 403 response
 * status.
 */
@Component("customAccessDeniedHandler")
@AllArgsConstructor
public class CustomAccessDeniedHandler implements AccessDeniedHandler {

  private final UnauthorizedExceptionHandler unauthorizedExceptionHandler;

  @Override
  public void handle(HttpServletRequest request, HttpServletResponse response,
      AccessDeniedException accessDeniedException) throws IOException {
    unauthorizedExceptionHandler.handleUnauthorizedException(response);
  }
}

