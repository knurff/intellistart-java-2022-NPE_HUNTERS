package com.intellias.intellistart.interviewplanning.security;

import com.intellias.intellistart.interviewplanning.util.RoleParser;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.util.Date;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Generates JWT token.
 */
@Component
public class JwtGenerator {

  @Value("${jwt.token.secret}")
  private String secret;
  @Value("${jwt.token.expiration_millis}")
  private Long tokenExpirationMillis;

  /**
   * Generates JWT token by given {@link JwtUserDetails}.
   */
  public String generateToken(JwtUserDetails jwtUserDetails) {
    Claims claims = Jwts.claims().setSubject(jwtUserDetails.getUsername());
    claims.put("id", jwtUserDetails.getId());
    claims.put("first_name", jwtUserDetails.getFirstName());
    claims.put("last_name", jwtUserDetails.getLastName());
    claims.put("role", RoleParser.parse(jwtUserDetails));

    Date currentDate = new Date();
    Date expiresAt = new Date(currentDate.getTime() + tokenExpirationMillis);

    return Jwts.builder().setClaims(claims).setIssuedAt(currentDate).setExpiration(expiresAt)
        .signWith(SignatureAlgorithm.HS512, secret).compact();
  }
}
