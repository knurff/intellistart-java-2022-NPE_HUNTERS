package com.intellias.intellistart.interviewplanning.exception;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.io.OutputStream;
import javax.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

/**
 * Handles requests from unauthorized users and returns meaningful response with status code 403.
 */
@Component
@AllArgsConstructor
public class UnauthorizedExceptionHandler {

  private static final String NOT_AUTHORIZED_ERROR_CODE = "not_authorized";
  private static final String NOT_AUTHORIZED_ERROR_MESSAGE =
      "You are not authorized to use this functionality";

  private final ObjectMapper objectMapper;

  /**
   * Creates exception response for unauthorized requests and returns it to user.
   */
  public void handleUnauthorizedException(HttpServletResponse httpServletResponse)
      throws IOException {
    ExceptionResponse exceptionResponse = new ExceptionResponse(NOT_AUTHORIZED_ERROR_CODE,
        NOT_AUTHORIZED_ERROR_MESSAGE
    );
    httpServletResponse.setStatus(HttpServletResponse.SC_FORBIDDEN);
    httpServletResponse.setContentType(MediaType.APPLICATION_JSON_VALUE);
    OutputStream outputStream = httpServletResponse.getOutputStream();
    objectMapper.writeValue(outputStream, exceptionResponse);
    outputStream.flush();
  }
}
