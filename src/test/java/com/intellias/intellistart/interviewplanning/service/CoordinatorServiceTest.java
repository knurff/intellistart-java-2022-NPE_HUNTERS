package com.intellias.intellistart.interviewplanning.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import com.intellias.intellistart.interviewplanning.model.Booking;
import com.intellias.intellistart.interviewplanning.model.User;
import com.intellias.intellistart.interviewplanning.model.role.UserRole;
import java.util.List;
import org.aspectj.weaver.patterns.ConcreteCflowPointcut.Slot;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class CoordinatorServiceTest {
  private final CoordinatorService serviceMock = Mockito.mock(CoordinatorService.class);

  @Test
  void createBooking() {
    when(serviceMock.createBooking()).thenReturn(new Booking());
    final Booking newBooking = serviceMock.createBooking();

    assertNotNull(newBooking);
  }

  @Test
  void editSlot() {
    when(serviceMock.editSlot()).thenReturn(true);
    final boolean result = serviceMock.editSlot();

    assertTrue(result);
  }

  @Test
  void editBooking() {
    when(serviceMock.editBooking()).thenReturn(true);
    final boolean result = serviceMock.editBooking();

    assertTrue(result);
  }

  @Test
  void deleteBooking() {
    when(serviceMock.deleteBooking()).thenReturn(true);
    final boolean result = serviceMock.deleteBooking();

    assertTrue(result);
  }

  @Test
  void grantRoleForUser() {
    when(serviceMock.grantRoleForUser()).thenReturn(true);
    final boolean result = serviceMock.grantRoleForUser();

    assertTrue(result);
  }

  @Test
  void removeRoleFromUser() {
    when(serviceMock.removeRoleFromUser()).thenReturn(true);
    final boolean result = serviceMock.removeRoleFromUser();

    assertTrue(result);
  }

  @Test
  void getAllUsersSlots() {
    when(serviceMock.getAllUsersSlots()).thenReturn(List.of());
    final List<Slot> result = serviceMock.getAllUsersSlots();

    assertNotNull(result);
  }

  @Test
  void getUsersByRole() {
    final List<User> result = serviceMock.getUsersByRole();

    assertNotNull(result);
  }

  @Test
  void getAllInterviewers() {
    final List<User> expectedInterviewers = List.of(new User(UserRole.INTERVIEWER));
    when(serviceMock.getAllInterviewers()).thenReturn(expectedInterviewers);

    final List<User> actualInterviewers = serviceMock.getAllInterviewers();

    assertNotNull(actualInterviewers);
    assertEquals(expectedInterviewers.size(), actualInterviewers.size());
    assertEquals(actualInterviewers.get(0).getRole(), UserRole.INTERVIEWER);
    assertSame(expectedInterviewers, actualInterviewers);
  }

  @Test
  void getAllCoordinators() {
    final List<User> expectedCoordinators = List.of(new User(UserRole.COORDINATOR));

    when(serviceMock.getAllInterviewers()).thenReturn(expectedCoordinators);

    final List<User> actualCoordinators = serviceMock.getAllInterviewers();

    assertNotNull(actualCoordinators);
    assertFalse(actualCoordinators.isEmpty());
    assertEquals(expectedCoordinators.size(), actualCoordinators.size());
    assertEquals(actualCoordinators.get(0).getRole(), UserRole.COORDINATOR);
    assertSame(expectedCoordinators, actualCoordinators);
  }
}