package com.intellias.intellistart.interviewplanning.security;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.intellias.intellistart.interviewplanning.service.factory.JwtUserDetailsFactory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class JwtTokenParserTest {

  @Autowired
  private JwtTokenParser jwtTokenParser;
  @Autowired
  private JwtGenerator jwtGenerator;

  @Test
  void parseUserDetailsFromToken() {
    String token = jwtGenerator.generateToken(JwtUserDetailsFactory.createJwtUserDetailsWithCandidateRole());
    JwtUserDetails jwtUserDetails = jwtTokenParser.parseUserDetailsFromToken(token);

    String roleFromParsedUserDetails = jwtUserDetails.getAuthorities().stream().findFirst().get()
        .toString();

    assertEquals("test@test.com", jwtUserDetails.getUsername());
    assertEquals(("ROLE_CANDIDATE"), roleFromParsedUserDetails);
  }
}