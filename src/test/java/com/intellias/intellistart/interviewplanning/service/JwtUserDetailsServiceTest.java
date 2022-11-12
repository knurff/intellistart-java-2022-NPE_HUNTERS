package com.intellias.intellistart.interviewplanning.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.intellias.intellistart.interviewplanning.repository.UserRepository;
import com.intellias.intellistart.interviewplanning.security.JwtUserDetails;
import com.intellias.intellistart.interviewplanning.service.factory.UserFactory;
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
    assertEquals("ROLE_INTERVIEWER", retrieveRoleFromUserDetails(jwtUserDetails));
  }

  @Test
  void loadUserByUsernameWorksProperlyIfUserWasNotFound() {
    Mockito.when(userRepository.getUserByEmail(TEST_EMAIL))
        .thenReturn(Optional.empty());

    JwtUserDetails jwtUserDetails = (JwtUserDetails) jwtUserDetailsService.loadUserByUsername(
        TEST_EMAIL);

    assertEquals(TEST_EMAIL, jwtUserDetails.getUsername());
    assertEquals("ROLE_CANDIDATE", retrieveRoleFromUserDetails(jwtUserDetails));
  }

  private String retrieveRoleFromUserDetails(JwtUserDetails jwtUserDetails) {
    return jwtUserDetails.getAuthorities().stream().findFirst().get()
        .toString();
  }
}