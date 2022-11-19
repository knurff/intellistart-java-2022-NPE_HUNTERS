package com.intellias.intellistart.interviewplanning.util;

import com.intellias.intellistart.interviewplanning.exception.SecurityExceptionHandler;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import java.io.IOException;
import javax.servlet.http.HttpServletResponse;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Util class with methods to process JWT token.
 */
@Component
@NoArgsConstructor
public class JwtUtils {

  @Value("${jwt.token.secret}")
  private String secret;
  @Autowired
  private SecurityExceptionHandler securityExceptionHandler;

  /**
   * Validates JWT token.
   */
  public boolean validateToken(HttpServletResponse response, String token) throws IOException {
    try {
      Jwts.parser().setSigningKey(secret).parseClaimsJws(token);
      return true;
    } catch (ExpiredJwtException ex) {
      securityExceptionHandler.handleExpiredJwtException(response);
    } catch (IllegalArgumentException ex) {
      securityExceptionHandler.handleIllegalArgumentException(response);
    } catch (MalformedJwtException ex) {
      securityExceptionHandler.handleMalformedJwtException(response);
    } catch (UnsupportedJwtException ex) {
      securityExceptionHandler.handleUnsupportedJwtException(response);
    } catch (SignatureException ex) {
      securityExceptionHandler.handleSignatureException(response);
    }
    return false;
  }

  /**
   * Retrieves subject from token.
   */
  public String getTokenSubject(String token) {
    return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody().getSubject();
  }

  /**
   * Retrieves claim from token by name.
   */
  public String getTokenClaims(String token, String claimName) {
    return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody()
        .get(claimName, String.class);
  }
}
