package com.intellias.intellistart.interviewplanning.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import com.intellias.intellistart.interviewplanning.model.Booking;
import com.intellias.intellistart.interviewplanning.model.InterviewerSlot;
import com.intellias.intellistart.interviewplanning.model.User;
import com.intellias.intellistart.interviewplanning.model.role.UserRole;
import com.intellias.intellistart.interviewplanning.util.InterviewerSlotFactory;
import java.util.List;
import org.aspectj.weaver.patterns.ConcreteCflowPointcut.Slot;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class CoordinatorServiceTest {

  private final CoordinatorService coordinatorServiceMock = Mockito.mock(CoordinatorService.class);


  @Test
  void createBooking() {
    when(coordinatorServiceMock.createBooking()).thenReturn(new Booking());
    final Booking newBooking = coordinatorServiceMock.createBooking();

    assertNotNull(newBooking);
  }

  @Test
  void editSlotWorkingProperly() {

    InterviewerSlot interviewerSlot = InterviewerSlotFactory.createInterviewerSlot();

    InterviewerSlot interviewerSlotToUpdate = InterviewerSlotFactory.createAnotherInterviewerSlot();
    interviewerSlotToUpdate.setId(1L);
    interviewerSlotToUpdate.setInterviewerId(new User(UserRole.INTERVIEWER));

    when(coordinatorServiceMock.editSlot(1L, 1L, interviewerSlot)).thenReturn(interviewerSlot);
    InterviewerSlot resultSlot = coordinatorServiceMock.editSlot(1L, 1L, interviewerSlot);

    assertEquals(resultSlot, interviewerSlot);
  }

  @Test
  void editBooking() {
    when(coordinatorServiceMock.editBooking()).thenReturn(true);
    final boolean result = coordinatorServiceMock.editBooking();

    assertTrue(result);
  }

  @Test
  void deleteBooking() {
    when(coordinatorServiceMock.deleteBooking()).thenReturn(true);
    final boolean result = coordinatorServiceMock.deleteBooking();

    assertTrue(result);
  }

  @Test
  void grantRoleForUser() {
    when(coordinatorServiceMock.grantRoleForUser()).thenReturn(true);
    final boolean result = coordinatorServiceMock.grantRoleForUser();

    assertTrue(result);
  }

  @Test
  void removeRoleFromUser() {
    when(coordinatorServiceMock.removeRoleFromUser()).thenReturn(true);
    final boolean result = coordinatorServiceMock.removeRoleFromUser();

    assertTrue(result);
  }

  @Test
  void getAllUsersSlots() {
    when(coordinatorServiceMock.getAllUsersSlots()).thenReturn(List.of());
    final List<Slot> result = coordinatorServiceMock.getAllUsersSlots();

    assertNotNull(result);
  }

  @Test
  void getUsersByRole() {
    final List<User> result = coordinatorServiceMock.getUsersByRole();

    assertNotNull(result);
  }

  @Test
  void getAllInterviewers() {
    final List<User> expectedInterviewers = List.of(new User(UserRole.INTERVIEWER));
    when(coordinatorServiceMock.getAllInterviewers()).thenReturn(expectedInterviewers);

    final List<User> actualInterviewers = coordinatorServiceMock.getAllInterviewers();

    assertNotNull(actualInterviewers);
    assertEquals(expectedInterviewers.size(), actualInterviewers.size());
    assertEquals(actualInterviewers.get(0).getRole(), UserRole.INTERVIEWER);
    assertSame(expectedInterviewers, actualInterviewers);
  }

  @Test
  void getAllCoordinators() {
    final List<User> expectedCoordinators = List.of(new User(UserRole.COORDINATOR));

    when(coordinatorServiceMock.getAllInterviewers()).thenReturn(expectedCoordinators);

    final List<User> actualCoordinators = coordinatorServiceMock.getAllInterviewers();

    assertNotNull(actualCoordinators);
    assertFalse(actualCoordinators.isEmpty());
    assertEquals(expectedCoordinators.size(), actualCoordinators.size());
    assertEquals(actualCoordinators.get(0).getRole(), UserRole.COORDINATOR);
    assertSame(expectedCoordinators, actualCoordinators);
  }
}