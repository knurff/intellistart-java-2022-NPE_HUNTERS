package com.intellias.intellistart.interviewplanning.controller.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.intellias.intellistart.interviewplanning.security.JwtUserDetails;
import com.intellias.intellistart.interviewplanning.util.RoleParser;
import lombok.Data;

/**
 * Contains response information with authenticated user data.
 */
@Data
public class AuthenticationResponse {

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

  @JsonInclude(Include.NON_NULL)
  private Long id;
  private String firstName;
  private String lastName;
  private String email;
  private String role;
  private String token;
}
