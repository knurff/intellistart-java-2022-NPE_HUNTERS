package com.intellias.intellistart.interviewplanning.service.factory;

import com.intellias.intellistart.interviewplanning.security.JwtUserDetails;
import java.util.Set;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

public class JwtUserDetailsFactory {

  private static final String TEST_EMAIL = "test@test.com";
  private static final String ROLE_CANDIDATE = "ROLE_CANDIDATE";
  private static final String TEST_FIRST_NAME = "First_Name";
  private static final String TEST_LAST_NAME = "Last_Name";

  public static JwtUserDetails createJwtUserDetailsWithCandidateRole() {
    JwtUserDetails jwtUserDetails = new JwtUserDetails(TEST_EMAIL, TEST_EMAIL,
        Set.of(new SimpleGrantedAuthority(ROLE_CANDIDATE)));
    jwtUserDetails.setFirstName(TEST_FIRST_NAME);
    jwtUserDetails.setLastName(TEST_LAST_NAME);
    return jwtUserDetails;
  }

}