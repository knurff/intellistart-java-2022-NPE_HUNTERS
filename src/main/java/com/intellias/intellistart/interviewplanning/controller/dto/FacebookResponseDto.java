package com.intellias.intellistart.interviewplanning.controller.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * FacebookResponse dto, contains user data fields from Facebook.
 */
@Data
public class FacebookResponseDto {

  private Long facebookId;
  @JsonProperty("first_name")
  private String firstName;
  @JsonProperty("last_name")
  private String lastName;
  private String email;

}
