package com.intellias.intellistart.interviewplanning.controller.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.intellias.intellistart.interviewplanning.security.JwtUserDetails;
import com.intellias.intellistart.interviewplanning.util.RoleParser;
import lombok.Getter;

/**
 * Contains response information with authenticated user data.
 */
@Getter
public class AuthenticationResponse {

  @JsonInclude(Include.NON_NULL)
  private final Long id;
  private final String firstName;
  private final String lastName;
  private final String email;
  private final String role;
  private final String token;

  /**
   * Constructor, that creates AuthenticationResponse with fields values from given JwtUserDetails
   * and JWT token.
   */
  public AuthenticationResponse(JwtUserDetails jwtUserDetails, String token) {
    this.id = jwtUserDetails.getId();
    this.firstName = jwtUserDetails.getFirstName();
    this.lastName = jwtUserDetails.getLastName();
    this.email = jwtUserDetails.getUsername();
    this.role = RoleParser.parse(jwtUserDetails);
    this.token = token;
  }
}
