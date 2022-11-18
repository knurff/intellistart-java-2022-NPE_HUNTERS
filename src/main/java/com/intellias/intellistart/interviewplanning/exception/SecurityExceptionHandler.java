package com.intellias.intellistart.interviewplanning.exception;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.io.OutputStream;
import javax.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

/**
 * Handles security exceptions and returns meaningful response with 401 response status.
 */
@Component
@AllArgsConstructor
public class SecurityExceptionHandler {

  private static final String EXPIRED_JWT_ERROR_CODE = "token_expired";
  private static final String ILLEGAL_JWT_ERROR_CODE = "illegal_token";
  private static final String MALFORMED_JWT_ERROR_CODE = "malformed_token";
  private static final String UNSUPPORTED_JWT_ERROR_CODE = "unsupported_jwt";
  private static final String INVALID_SIGNATURE_ERROR_CODE = "invalid_signature";
  private static final String EXPIRED_JWT_ERROR_MESSAGE = "JWT token expired";
  private static final String ILLEGAL_JWT_ERROR_MESSAGE = "Token is null,empty or starts with "
      + "whitespace";
  private static final String MALFORMED_JWT_ERROR_MESSAGE = "Token is invalid";
  private static final String UNSUPPORTED_JWT_ERROR_MESSAGE = "JWT is not supported";
  private static final String INVALID_SIGNATURE_ERROR_MESSAGE = "Signature validation failed";
  private ObjectMapper objectMapper;

  public void handleExpiredJwtException(HttpServletResponse response) throws IOException {
    handleException(response, EXPIRED_JWT_ERROR_CODE, EXPIRED_JWT_ERROR_MESSAGE);
  }

  public void handleIllegalArgumentException(HttpServletResponse response) throws IOException {
    handleException(response, ILLEGAL_JWT_ERROR_CODE, ILLEGAL_JWT_ERROR_MESSAGE);
  }

  public void handleMalformedJwtException(HttpServletResponse response) throws IOException {
    handleException(response, MALFORMED_JWT_ERROR_CODE, MALFORMED_JWT_ERROR_MESSAGE);
  }

  public void handleUnsupportedJwtException(HttpServletResponse response) throws IOException {
    handleException(response, UNSUPPORTED_JWT_ERROR_CODE, UNSUPPORTED_JWT_ERROR_MESSAGE);
  }

  public void handleSignatureException(HttpServletResponse response) throws IOException {
    handleException(response, INVALID_SIGNATURE_ERROR_CODE, INVALID_SIGNATURE_ERROR_MESSAGE);
  }


  private void handleException(HttpServletResponse httpServletResponse, String errorCode,
      String errorMessage) throws IOException {
    ExceptionResponse exceptionResponse = new ExceptionResponse(errorCode, errorMessage);
    httpServletResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
    httpServletResponse.setContentType(MediaType.APPLICATION_JSON_VALUE);
    OutputStream outputStream = httpServletResponse.getOutputStream();
    objectMapper.writeValue(outputStream, exceptionResponse);
    outputStream.flush();
  }
}
