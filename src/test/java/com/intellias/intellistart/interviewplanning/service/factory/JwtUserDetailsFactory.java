package com.intellias.intellistart.interviewplanning.service.factory;

import com.intellias.intellistart.interviewplanning.security.JwtUserDetails;
import java.util.List;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

public class JwtUserDetailsFactory {

  private static final String TEST_EMAIL = "test@test.com";

  public static JwtUserDetails createJwtUserDetailsWithCandidateRole() {
    return new JwtUserDetails(TEST_EMAIL, TEST_EMAIL,
        List.of(new SimpleGrantedAuthority("ROLE_CANDIDATE")));
  }

}
