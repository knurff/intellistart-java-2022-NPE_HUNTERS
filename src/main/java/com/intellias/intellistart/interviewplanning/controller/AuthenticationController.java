package com.intellias.intellistart.interviewplanning.controller;

import com.intellias.intellistart.interviewplanning.controller.dto.FacebookResponseDto;
import com.intellias.intellistart.interviewplanning.controller.dto.FacebookTokenDto;
import com.intellias.intellistart.interviewplanning.controller.response.AuthenticationResponse;
import com.intellias.intellistart.interviewplanning.security.FacebookTokenParser;
import com.intellias.intellistart.interviewplanning.security.JwtGenerator;
import com.intellias.intellistart.interviewplanning.security.JwtUserDetails;
import com.intellias.intellistart.interviewplanning.service.JwtUserDetailsService;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller, that handles authentication requests.
 */
@RestController
@AllArgsConstructor
public class AuthenticationController {

  private final AuthenticationManager authenticationManager;
  private final JwtUserDetailsService jwtService;
  private final JwtGenerator jwtGenerator;
  private final FacebookTokenParser facebookTokenParser;

  /**
   * Handles POST requests and performs login.
   *
   * @param facebookTokenDto dto, that contains facebook token.
   * @return response with user email and jwt token.
   */
  @PostMapping("/auth/login")
  public AuthenticationResponse login(@RequestBody FacebookTokenDto facebookTokenDto) {
    FacebookResponseDto facebookResponseDto = facebookTokenParser.parseUserData(
        facebookTokenDto.getToken());

    String email = facebookResponseDto.getEmail();
    authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, email));

    JwtUserDetails jwtUserDetails = (JwtUserDetails) jwtService.loadUserByUsername(
        email);
    String accessToken = jwtGenerator.generateToken(jwtUserDetails);

    return new AuthenticationResponse(email,
        accessToken);
  }
}
