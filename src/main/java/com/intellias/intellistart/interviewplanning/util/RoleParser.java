package com.intellias.intellistart.interviewplanning.util;

import com.intellias.intellistart.interviewplanning.security.JwtUserDetails;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * Util class with method for parsing role from given JwtUserDetails.
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class RoleParser {

  /**
   * Parses role from given JwtUserDetails.
   */
  public static String parse(JwtUserDetails jwtUserDetails) {
    return jwtUserDetails.getAuthorities().stream().findFirst().get().toString();
  }

}
