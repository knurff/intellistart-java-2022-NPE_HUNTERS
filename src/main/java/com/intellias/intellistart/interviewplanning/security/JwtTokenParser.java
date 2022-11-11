package com.intellias.intellistart.interviewplanning.security;


import com.intellias.intellistart.interviewplanning.service.JwtUserDetailsService;
import com.intellias.intellistart.interviewplanning.util.JwtUtils;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * Parses JWT token and retrieves user details from it.
 */
@Component
@AllArgsConstructor
public class JwtTokenParser {

  private final JwtUserDetailsService jwtService;
  private final JwtUtils jwtUtils;

  public JwtUserDetails parseUserDetailsFromToken(String token) {
    String email = jwtUtils.getTokenSubject(token);
    return (JwtUserDetails) jwtService.loadUserByUsername(email);
  }
}
