package com.intellias.intellistart.interviewplanning.exception;

import java.io.IOException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.access.AccessDeniedException;

@ExtendWith(MockitoExtension.class)
class CustomAccessDeniedHandlerTest {

  @Mock
  private UnauthorizedExceptionHandler unauthorizedExceptionHandler;
  @InjectMocks
  private CustomAccessDeniedHandler accessDeniedHandler;

  private static MockHttpServletRequest httpServletRequest;

  private static MockHttpServletResponse httpServletResponse;

  @BeforeEach
  void init() {
    httpServletRequest = new MockHttpServletRequest();
    httpServletResponse = new MockHttpServletResponse();
  }

  @Test
  void handleWorkingProperly() throws IOException {
    accessDeniedHandler.handle(httpServletRequest, httpServletResponse,
        new AccessDeniedException("Access denied"));

    Mockito.verify(unauthorizedExceptionHandler, Mockito.times(1))
        .handleUnauthorizedException(httpServletResponse);
  }
}