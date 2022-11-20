package com.intellias.intellistart.interviewplanning.security;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.intellias.intellistart.interviewplanning.service.factory.JwtUserDetailsFactory;
import com.intellias.intellistart.interviewplanning.util.JwtUtils;
import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

@ExtendWith(MockitoExtension.class)
class JwtTokenFilterTest {

  private static final String INVALID_BEARER_TOKEN = "Bearer INVALID_TOKEN";
  private static final String VALID_BEARER_TOKEN = "Bearer VALID_TOKEN";
  private static final String VALID_TOKEN = "VALID_TOKEN";
  @Mock
  private JwtTokenParser jwtTokenParser;
  @Mock
  private JwtUtils jwtUtils;
  @InjectMocks
  private JwtTokenFilter jwtTokenFilter;
  @Mock
  private static HttpServletRequest httpServletRequest;
  @Mock
  private static HttpServletResponse httpServletResponse;
  @Mock
  private static FilterChain filterChain;
  @Mock
  private SecurityContext securityContext;

  @BeforeEach
  void init() {
    SecurityContextHolder.setContext(securityContext);
  }

  @Test
  void doFilterInternalFiltersRequestIfBearerTokenIsAbsent() throws ServletException, IOException {
    jwtTokenFilter.doFilterInternal(httpServletRequest, httpServletResponse, filterChain);

    verify(filterChain,
        times(1)).doFilter(httpServletRequest, httpServletResponse);
  }

  @Test
  void doFilterInternalFiltersRequestIfTokenIsNotValid() throws ServletException, IOException {
    when(httpServletRequest.getHeader("Authorization")).thenReturn(INVALID_BEARER_TOKEN);
    jwtTokenFilter.doFilterInternal(httpServletRequest, httpServletResponse, filterChain);

    verify(filterChain,
        times(0)).doFilter(httpServletRequest, httpServletResponse);

  }

  @Test
  void doFilterInternalWorkingProperly() throws ServletException, IOException {
    when(httpServletRequest.getHeader("Authorization")).thenReturn(VALID_BEARER_TOKEN);
    when(jwtUtils.validateToken(any(HttpServletResponse.class), anyString())).thenReturn(
        Boolean.TRUE);
    when(jwtTokenParser.parseUserDetailsFromToken(VALID_TOKEN)).thenReturn(
        JwtUserDetailsFactory.createJwtUserDetailsWithCandidateRole());

    jwtTokenFilter.doFilterInternal(httpServletRequest, httpServletResponse, filterChain);

    verify(securityContext, times(1)).setAuthentication(
        any(Authentication.class));
    verify(filterChain,
        times(1)).doFilter(httpServletRequest, httpServletResponse);
  }
}