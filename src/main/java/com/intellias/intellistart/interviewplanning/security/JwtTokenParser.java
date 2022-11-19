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

  /**
   * Retrieves JwtUserDetails from token.
   */
  public JwtUserDetails parseUserDetailsFromToken(String token) {
    String email = jwtUtils.getTokenSubject(token);
    String firstName = jwtUtils.getTokenClaims(token, "first_name");
    String lastName = jwtUtils.getTokenClaims(token, "last_name");
    return (JwtUserDetails) jwtService.loadFullUserInfo(email, firstName, lastName);
  }
}
