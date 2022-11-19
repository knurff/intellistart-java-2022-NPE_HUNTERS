package com.intellias.intellistart.interviewplanning.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.intellias.intellistart.interviewplanning.repository.UserRepository;
import com.intellias.intellistart.interviewplanning.security.JwtUserDetails;
import com.intellias.intellistart.interviewplanning.service.factory.UserFactory;
import com.intellias.intellistart.interviewplanning.util.RoleParser;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

@ExtendWith(MockitoExtension.class)
class JwtUserDetailsServiceTest {

  private static final String TEST_EMAIL = "test@test.com";
  private static final String TEST_FIRST_NAME = "First_Name";
  private static final String TEST_LAST_NAME = "Last_Name";
  private static final String ROLE_CANDIDATE = "ROLE_CANDIDATE";
  private static final String ROLE_INTERVIEWER = "ROLE_INTERVIEWER";
  @Mock
  private UserRepository userRepository;

  @Mock
  private PasswordEncoder passwordEncoder;
  @InjectMocks
  private JwtUserDetailsService jwtUserDetailsService;

  @Test
  void loadUserByUsernameWorksProperlyIfUserWasFound() {
    Mockito.when(userRepository.getUserByEmail(TEST_EMAIL))
        .thenReturn(Optional.of(UserFactory.createInterviewer()));

    JwtUserDetails jwtUserDetails = (JwtUserDetails) jwtUserDetailsService.loadUserByUsername(
        TEST_EMAIL);

    assertEquals(TEST_EMAIL, jwtUserDetails.getUsername());
    assertEquals(ROLE_INTERVIEWER, RoleParser.parse(jwtUserDetails));
  }

  @Test
  void loadUserByUsernameWorksProperlyIfUserWasNotFound() {
    Mockito.when(userRepository.getUserByEmail(TEST_EMAIL))
        .thenReturn(Optional.empty());

    JwtUserDetails jwtUserDetails = (JwtUserDetails) jwtUserDetailsService.loadUserByUsername(
        TEST_EMAIL);

    assertEquals(TEST_EMAIL, jwtUserDetails.getUsername());
    assertEquals(ROLE_CANDIDATE, RoleParser.parse(jwtUserDetails));
  }

  @Test
  void loadFullUserInfoWorkingProperly() {
    Mockito.when(userRepository.getUserByEmail(TEST_EMAIL))
        .thenReturn(Optional.empty());

    JwtUserDetails jwtUserDetails = (JwtUserDetails) jwtUserDetailsService.loadFullUserInfo(
        TEST_EMAIL,
        TEST_FIRST_NAME, TEST_LAST_NAME);

    assertEquals(TEST_EMAIL, jwtUserDetails.getUsername());
    assertEquals(TEST_FIRST_NAME, jwtUserDetails.getFirstName());
    assertEquals(TEST_LAST_NAME, jwtUserDetails.getLastName());
    assertEquals(ROLE_CANDIDATE, RoleParser.parse(jwtUserDetails));

  }
}