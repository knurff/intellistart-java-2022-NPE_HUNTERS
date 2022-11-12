package com.intellias.intellistart.interviewplanning.security;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.intellias.intellistart.interviewplanning.service.factory.JwtUserDetailsFactory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class JwtGeneratorTest {

  @Autowired
  private JwtGenerator jwtGenerator;

  @Test
  void generateToken() {
    String token = jwtGenerator.generateToken(JwtUserDetailsFactory.createJwtUserDetailsWithCandidateRole());
    assertNotNull(token);
  }
}