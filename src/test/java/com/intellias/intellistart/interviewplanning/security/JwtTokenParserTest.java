package com.intellias.intellistart.interviewplanning.security;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.intellias.intellistart.interviewplanning.service.JwtUserDetailsService;
import com.intellias.intellistart.interviewplanning.service.factory.JwtUserDetailsFactory;
import com.intellias.intellistart.interviewplanning.util.JwtUtils;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

@ExtendWith(MockitoExtension.class)
class JwtTokenParserTest {

  private static JwtGenerator jwtGenerator;
  @Mock
  private JwtUserDetailsService jwtUserDetailsService;
  @Mock
  private JwtUtils jwtUtils;
  @InjectMocks
  private JwtTokenParser jwtTokenParser;

  @BeforeAll
  public static void setUp() {
    jwtGenerator = new JwtGenerator();
    ReflectionTestUtils.setField(jwtGenerator, "secret", "test_secret");
    ReflectionTestUtils.setField(jwtGenerator, "tokenExpirationMillis", 100000000L);
  }

  @Test
  void parseUserDetailsFromToken() {
    JwtUserDetails jwtUserDetails = JwtUserDetailsFactory.createJwtUserDetailsWithCandidateRole();
    String token = jwtGenerator.generateToken(jwtUserDetails);

    Mockito.when(jwtTokenParser.parseUserDetailsFromToken(Mockito.anyString())).
        thenReturn(jwtUserDetails);

    JwtUserDetails jwtUserDetailsFromToken = jwtTokenParser.parseUserDetailsFromToken(token);
    String roleFromParsedUserDetails = jwtUserDetailsFromToken.getAuthorities().stream().findFirst()
        .get()
        .toString();

    assertEquals("test@test.com", jwtUserDetailsFromToken.getUsername());
    assertEquals(("ROLE_CANDIDATE"), roleFromParsedUserDetails);
  }
}