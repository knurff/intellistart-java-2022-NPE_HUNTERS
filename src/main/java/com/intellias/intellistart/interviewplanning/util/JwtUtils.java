package com.intellias.intellistart.interviewplanning.util;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Util class with methods to process JWT token.
 */
@Component
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class JwtUtils {

  @Value("${jwt.token.secret}")
  private String secret;

  /**
   * Validates JWT token.
   */
  public boolean validateToken(String token) {
    try {
      Jwts.parser().setSigningKey(secret).parseClaimsJws(token);
      return true;
    } catch (ExpiredJwtException ex) {
      throw new SecurityException("Token expired");
    } catch (IllegalArgumentException ex) {
      throw new SecurityException("Token is null,empty or only whitespace");
    } catch (MalformedJwtException ex) {
      throw new SecurityException("Token is invalid");
    } catch (UnsupportedJwtException ex) {
      throw new SecurityException("JWT is not supported");
    } catch (SignatureException ex) {
      throw new SecurityException("Signature validation failed");
    }
  }

  public String getTokenSubject(String token) {
    return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody().getSubject();
  }
}
