package com.intellias.intellistart.interviewplanning.service.factory;

import com.intellias.intellistart.interviewplanning.security.JwtGenerator;
import com.intellias.intellistart.interviewplanning.security.JwtUserDetails;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.util.Date;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class JwtTokenFactory {

  private static final String TEST_EMAIL = "test@test.com";
  private static final String MALFORMED_TOKEN = "malformed_token";
  private JwtGenerator jwtGenerator;
  @Value("${jwt.token.secret}")
  private static final String SECRET = "test_secret";

  public String generateTestToken() {
    JwtUserDetails jwtUserDetails = JwtUserDetailsFactory.createJwtUserDetailsWithCandidateRole();
    return jwtGenerator.generateToken(jwtUserDetails);
  }

  public String generateExpiredToken() {
    return Jwts.builder().setClaims(Jwts.claims().setSubject(TEST_EMAIL))
        .setExpiration(new Date())
        .signWith(SignatureAlgorithm.HS512, SECRET)
        .compact();
  }

  public String generateMalformedToken() {
    return MALFORMED_TOKEN;
  }

  public String generateUnsupportedToken() {
    return Jwts.builder().setClaims(Jwts.claims().setSubject(TEST_EMAIL))
        .compact();
  }

  public String generateTokenWithWrongSignature() {
    String token = generateTestToken();
    return token.substring(0, token.length() - 3);
  }

}
