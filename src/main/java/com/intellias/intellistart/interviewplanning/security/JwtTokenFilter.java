package com.intellias.intellistart.interviewplanning.security;

import com.intellias.intellistart.interviewplanning.util.JwtUtils;
import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.web.filter.OncePerRequestFilter;

/**
 * Filter, that extracts JWT tokens, validates them and sets authentication context.
 */
@Component
@AllArgsConstructor
public class JwtTokenFilter extends OncePerRequestFilter {


  private final JwtTokenParser jwtTokenParser;
  private final JwtUtils jwtUtils;

  @Override
  protected void doFilterInternal(@NonNull HttpServletRequest request,
      @NonNull HttpServletResponse response,
      @NonNull FilterChain filterChain) throws ServletException, IOException {
    if (!hasAuthorizationBearer(request)) {
      filterChain.doFilter(request, response);
      return;
    }

    String accessToken = getAccessToken(request);

    if (!jwtUtils.validateToken(response, accessToken)) {
      return;
    }
    setAuthenticationContext(accessToken, request);
    filterChain.doFilter(request, response);
  }

  private void setAuthenticationContext(String token, HttpServletRequest httpServletRequest) {
    UserDetails userDetails = jwtTokenParser.parseUserDetailsFromToken(token);

    UsernamePasswordAuthenticationToken
        authentication = new UsernamePasswordAuthenticationToken(userDetails, null,
        userDetails.getAuthorities());

    authentication.setDetails(
        new WebAuthenticationDetailsSource().buildDetails(httpServletRequest));

    SecurityContextHolder.getContext().setAuthentication(authentication);
  }

  private String getAccessToken(HttpServletRequest request) {
    String header = request.getHeader("Authorization");
    return header.split(" ")[1].trim();
  }

  private boolean hasAuthorizationBearer(HttpServletRequest request) {
    String header = request.getHeader("Authorization");
    return !ObjectUtils.isEmpty(header) && header.startsWith("Bearer");
  }
}
