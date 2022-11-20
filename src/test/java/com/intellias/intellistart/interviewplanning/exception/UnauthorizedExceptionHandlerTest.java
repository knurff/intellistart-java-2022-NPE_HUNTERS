package com.intellias.intellistart.interviewplanning.exception;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockHttpServletResponse;

@ExtendWith(MockitoExtension.class)
class UnauthorizedExceptionHandlerTest {

  @Mock
  private ObjectMapper objectMapper;
  @InjectMocks
  private UnauthorizedExceptionHandler unauthorizedExceptionHandler;

  private static MockHttpServletResponse httpServletResponse;

  @BeforeEach
  void init() {
    httpServletResponse = new MockHttpServletResponse();
  }

  @Test
  void handleUnauthorizedExceptionWorkingProperly() throws IOException {
    unauthorizedExceptionHandler.handleUnauthorizedException(httpServletResponse);

    assertEquals(403, httpServletResponse.getStatus());
  }
}