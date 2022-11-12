package com.intellias.intellistart.interviewplanning.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.intellias.intellistart.interviewplanning.exception.SecurityExceptionHandler;
import com.intellias.intellistart.interviewplanning.security.JwtGenerator;
import com.intellias.intellistart.interviewplanning.service.factory.JwtTokenFactory;
import java.io.IOException;
import org.apache.logging.log4j.util.Strings;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.util.ReflectionTestUtils;

@ExtendWith(MockitoExtension.class)
class JwtUtilsTest {

  private static final String TEST_EMAIL = "test@test.com";
  @Value("${jwt.token.secret}")
  private static String secret;
  private static MockHttpServletResponse httpServletResponse;
  private static JwtTokenFactory jwtTokenFactory;
  @Mock
  private SecurityExceptionHandler securityExceptionHandler;
  @InjectMocks
  private JwtUtils jwtUtils;

  @BeforeAll
  public static void setUp() {
    JwtGenerator jwtGenerator = new JwtGenerator();
    httpServletResponse = new MockHttpServletResponse();
    jwtTokenFactory = new JwtTokenFactory(jwtGenerator, secret);

    ReflectionTestUtils.setField(jwtGenerator, "secret", "test_secret");
    ReflectionTestUtils.setField(jwtGenerator, "tokenExpirationMillis", 100000000L);
    ReflectionTestUtils.setField(jwtTokenFactory, "secret", "test_secret");
  }

  @BeforeEach
  public void init() {
    ReflectionTestUtils.setField(jwtUtils, "secret", "test_secret");
  }

  @Test
  void validateTokenWorkingProperly() throws IOException {
    assertTrue(jwtUtils.validateToken(httpServletResponse, jwtTokenFactory.generateTestToken()));
  }

  @Test
  void validateTokenThrowsAnExceptionIfTokenIsExpired() throws IOException {
    assertFalse(
        jwtUtils.validateToken(httpServletResponse, jwtTokenFactory.generateExpiredToken()));
    Mockito.verify(securityExceptionHandler, Mockito.times(1))
        .handleExpiredJwtException(httpServletResponse);
  }

  @Test
  void validateTokenThrowsAnExceptionIfTokenIsNullOrEmptyOrWhitespace() throws IOException {
    assertFalse(jwtUtils.validateToken(httpServletResponse, null));
    assertFalse(jwtUtils.validateToken(httpServletResponse, Strings.EMPTY));
    assertFalse(jwtUtils.validateToken(httpServletResponse, " "));
    Mockito.verify(securityExceptionHandler, Mockito.times(3))
        .handleIllegalArgumentException(httpServletResponse);

  }

  @Test
  void validateThrowsAnExceptionIfTokenIsMalformed() throws IOException {
    assertFalse(
        jwtUtils.validateToken(httpServletResponse, jwtTokenFactory.generateMalformedToken()));
    Mockito.verify(securityExceptionHandler, Mockito.times(1))
        .handleMalformedJwtException(httpServletResponse);
  }

  @Test
  void validateTokenThrowsAnExceptionIfSignatureValidationFailed() throws IOException {
    assertFalse(jwtUtils.validateToken(httpServletResponse,
        jwtTokenFactory.generateTokenWithWrongSignature()));
    Mockito.verify(securityExceptionHandler, Mockito.times(1))
        .handleSignatureException(httpServletResponse);
  }

  @Test
  void validateTokenThrowsAnExceptionIfTokenIsNotSupported() throws IOException {
    assertFalse(
        jwtUtils.validateToken(httpServletResponse, jwtTokenFactory.generateUnsupportedToken()));
    Mockito.verify(securityExceptionHandler, Mockito.times(1))
        .handleUnsupportedJwtException(httpServletResponse);
  }

  @Test
  void getTokenSubjectWorkingProperly() {
    String email = jwtUtils.getTokenSubject(jwtTokenFactory.generateTestToken());

    assertEquals(TEST_EMAIL, email);
  }
}
