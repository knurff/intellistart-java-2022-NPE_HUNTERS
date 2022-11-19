package com.intellias.intellistart.interviewplanning.util;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.intellias.intellistart.interviewplanning.security.JwtUserDetails;
import com.intellias.intellistart.interviewplanning.service.factory.JwtUserDetailsFactory;
import org.junit.jupiter.api.Test;

class RoleParserTest {

  private static final String ROLE_CANDIDATE = "ROLE_CANDIDATE";

  @Test
  void parseWorkingProperly() {
    JwtUserDetails jwtUserDetails = JwtUserDetailsFactory.createJwtUserDetailsWithCandidateRole();

    assertEquals(ROLE_CANDIDATE, RoleParser.parse(jwtUserDetails));
  }
}