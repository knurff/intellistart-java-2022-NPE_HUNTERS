package com.intellias.intellistart.interviewplanning.controller.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Contains response information with authenticated user data.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AuthenticationResponse {

  @JsonInclude(Include.NON_NULL)
  private Long id;
  private String firstName;
  private String lastName;
  private String email;
  private String role;
  private String token;
}
