package com.intellias.intellistart.interviewplanning.security;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.intellias.intellistart.interviewplanning.service.factory.JwtUserDetailsFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

class JwtGeneratorTest {

  private static JwtGenerator jwtGenerator;

  @BeforeAll
  public static void setUp() {
    jwtGenerator = new JwtGenerator();
    ReflectionTestUtils.setField(jwtGenerator, "secret", "test_secret");
    ReflectionTestUtils.setField(jwtGenerator, "tokenExpirationMillis", 100000000L);
  }

  @Test
  void generateToken() {
    String token = jwtGenerator.generateToken(
        JwtUserDetailsFactory.createJwtUserDetailsWithCandidateRole());
    assertNotNull(token);
  }
}