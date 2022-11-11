package com.intellias.intellistart.interviewplanning.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * FacebookResponse dto, contains user data fields.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FacebookResponseDto {

  private Long id;
  private String email;

}
