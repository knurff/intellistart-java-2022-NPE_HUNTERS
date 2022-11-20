package com.intellias.intellistart.interviewplanning.util;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Set;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

@ExtendWith(MockitoExtension.class)
class RequestParserTest {

  private static final String TEST_EMAIL = "test@test.com";
  private static final String ROLE_CANDIDATE = "ROLE_CANDIDATE";

  @Mock
  private SecurityContext securityContext;

  @Test
  void getUserEmailFromTokenWorkingProperly() {
    createSecurityContextAndConfigureMock();

    assertEquals(TEST_EMAIL, RequestParser.getUserEmailFromToken());
  }

  @Test
  void getUserRoleFromTokenWorkingProperly() {
    createSecurityContextAndConfigureMock();

    assertEquals(ROLE_CANDIDATE, RequestParser.getUserRoleFromToken());
  }

  private void createSecurityContextAndConfigureMock() {
    Authentication authentication = new UsernamePasswordAuthenticationToken(TEST_EMAIL, TEST_EMAIL,
        Set.of(new SimpleGrantedAuthority(ROLE_CANDIDATE)));

    Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);

    SecurityContextHolder.setContext(securityContext);
  }
}