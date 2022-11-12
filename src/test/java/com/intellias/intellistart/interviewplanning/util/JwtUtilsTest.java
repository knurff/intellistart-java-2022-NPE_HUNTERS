package com.intellias.intellistart.interviewplanning.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import com.intellias.intellistart.interviewplanning.service.factory.JwtTokenFactory;
import java.io.IOException;
import org.apache.logging.log4j.util.Strings;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpServletResponse;

@SpringBootTest
class JwtUtilsTest {

  private static final String TEST_EMAIL = "test@test.com";
  private static MockHttpServletResponse httpServletResponse;
  @Autowired
  private JwtTokenFactory jwtTokenFactory;
  @Autowired
  private JwtUtils jwtUtils;

  @BeforeAll
  static void init() {
    httpServletResponse = new MockHttpServletResponse();
  }

  @Test
  void validateTokenWorkingProperlyIfTokenIsExpired() throws IOException {

    assertFalse(
        jwtUtils.validateToken(httpServletResponse, jwtTokenFactory.generateExpiredToken()));
  }

  @Test
  void validateTokenWorkingProperlyIfTokenIsNullOrEmptyOrWhitespace() throws IOException {

    assertFalse(jwtUtils.validateToken(httpServletResponse, null));
    assertFalse(jwtUtils.validateToken(httpServletResponse, Strings.EMPTY));
    assertFalse(jwtUtils.validateToken(httpServletResponse, " "));
  }

  @Test
  void validateTokenWorkingProperlyIfTokenIsMalformed() throws IOException {

    assertFalse(
        jwtUtils.validateToken(httpServletResponse, jwtTokenFactory.generateMalformedToken()));
  }

  @Test
  void validateTokenWorkingProperlyIfSignatureValidationFailed() throws IOException {

    assertFalse(jwtUtils.validateToken(httpServletResponse,
        jwtTokenFactory.generateTokenWithWrongSignature()));
  }

  @Test
  void validateTokenWorkingProperlyIfTokenIsNotSupported() throws IOException {

    assertFalse(
        jwtUtils.validateToken(httpServletResponse, jwtTokenFactory.generateUnsupportedToken()));
  }

  @Test
  void getTokenSubjectWorkingProperly() {
    String email = jwtUtils.getTokenSubject(jwtTokenFactory.generateTestToken());

    assertEquals(TEST_EMAIL, email);
  }
}
