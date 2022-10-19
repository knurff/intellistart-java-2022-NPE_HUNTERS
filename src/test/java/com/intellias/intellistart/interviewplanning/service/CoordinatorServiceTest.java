package com.intellias.intellistart.interviewplanning.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import com.intellias.intellistart.interviewplanning.model.Booking;
import com.intellias.intellistart.interviewplanning.model.CandidateSlot;
import com.intellias.intellistart.interviewplanning.model.User;
import com.intellias.intellistart.interviewplanning.model.role.UserRole;
import com.intellias.intellistart.interviewplanning.repository.UserRepository;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class CoordinatorServiceTest {

  @Mock
  private UserRepository userRepository;
  private CoordinatorService coordinatorService;

  @BeforeEach
  void setup() {
    coordinatorService = new CoordinatorService(userRepository);
  }

  @Test
  void createBooking() {

    final Booking newBooking = coordinatorService.createBooking();

    assertNotNull(newBooking);
  }

  @Test
  void editSlot() {

    final boolean result = coordinatorService.editSlot();

    assertTrue(result);
  }

  @Test
  void editBooking() {

    final boolean result = coordinatorService.editBooking();

    assertTrue(result);
  }

  @Test
  void grantRoleForUser() {

    final boolean result = coordinatorService.grantRoleForUser();

    assertTrue(result);
  }

  @Test
  void removeRoleFromUser() {
    final boolean result = coordinatorService.removeRoleFromUser();

    assertTrue(result);
  }

  @Test
  void getAllUsersSlots() {
    final List<CandidateSlot> result = coordinatorService.getAllUsersSlots();

    assertNotNull(result);
  }

  @Test
  void getUsersByRole() {
    final List<User> result = coordinatorService.getUsersByRole();

    assertNotNull(result);
  }

  @Test
  void getAllInterviewers() {
    final List<User> expectedInterviewers = List.of(new User(UserRole.INTERVIEWER));
    when(userRepository.getAllByRole(UserRole.INTERVIEWER)).thenReturn(expectedInterviewers);
    final List<User> actualInterviewers = coordinatorService.getAllInterviewers();

    assertNotNull(actualInterviewers);
    assertEquals(expectedInterviewers.size(), actualInterviewers.size());
    assertEquals(UserRole.INTERVIEWER, actualInterviewers.get(0).getRole());
    assertSame(expectedInterviewers, actualInterviewers);
  }

  @Test
  void getAllCoordinators() {
    final List<User> expectedCoordinators = List.of(new User(UserRole.COORDINATOR));
    when(userRepository.getAllByRole(UserRole.COORDINATOR)).thenReturn(expectedCoordinators);
    final List<User> actualCoordinators = coordinatorService.getAllCoordinators();

    assertNotNull(actualCoordinators);
    assertFalse(actualCoordinators.isEmpty());
    assertEquals(expectedCoordinators.size(), actualCoordinators.size());
    assertEquals(UserRole.COORDINATOR, actualCoordinators.get(0).getRole());
    assertSame(expectedCoordinators, actualCoordinators);
  }
}