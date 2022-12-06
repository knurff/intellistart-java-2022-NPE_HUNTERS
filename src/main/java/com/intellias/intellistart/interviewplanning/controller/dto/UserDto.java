package com.intellias.intellistart.interviewplanning.controller.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.intellias.intellistart.interviewplanning.model.role.UserRole;
import com.intellias.intellistart.interviewplanning.security.JwtUserDetails;
import com.intellias.intellistart.interviewplanning.util.RoleParser;
import lombok.Getter;

/**
 * A DTO with information about logged-in user.
 */
@Getter
public class UserDto {
  @JsonInclude(value = Include.NON_NULL)
  private String firstName;

  @JsonInclude(value = Include.NON_NULL)
  private String lastName;
  private final String email;
  private final String role;
  @JsonInclude(value = Include.NON_NULL)
  private final Long id;

  /**
   * Constructs DTO using details received from JWT.
   *
   * @param details user details received from JWT
   */
  public UserDto(JwtUserDetails details) {
    this.firstName = details.getFirstName();
    this.lastName = details.getLastName();
    this.email = details.getUsername();
    this.role = RoleParser.parse(details).split("_")[1];
    this.id = details.getId();
  }

  /**
   * Constructs DTO by email, role, id.
   *
   * @param email user's email
   * @param role user's role
   * @param id user's id
   */
  public UserDto(String email, UserRole role, Long id) {
    this.email = email;
    this.role = role.toString();
    this.id = id;
  }
}
