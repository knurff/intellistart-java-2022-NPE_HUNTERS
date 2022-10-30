package com.intellias.intellistart.interviewplanning.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

import com.intellias.intellistart.interviewplanning.controller.dto.DashboardDto;
import com.intellias.intellistart.interviewplanning.model.Booking;
import com.intellias.intellistart.interviewplanning.model.CandidateSlot;
import com.intellias.intellistart.interviewplanning.model.InterviewerSlot;
import com.intellias.intellistart.interviewplanning.model.User;
import com.intellias.intellistart.interviewplanning.model.role.UserRole;
import com.intellias.intellistart.interviewplanning.service.factory.InterviewerSlotFactory;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.aspectj.weaver.patterns.ConcreteCflowPointcut.Slot;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class CoordinatorServiceTest {

  @Mock
  private CoordinatorService coordinatorServiceMock;
  @Mock
  private InterviewerService interviewerService;
  @Mock
  private CandidateService candidateService;
  @Mock
  private BookingService bookingService;
  @InjectMocks
  private CoordinatorService coordinatorService;

  @Test
  void editSlotWorkingProperly() {

    InterviewerSlot interviewerSlot = InterviewerSlotFactory.createInterviewerSlot();

    InterviewerSlot interviewerSlotToUpdate = InterviewerSlotFactory.createAnotherInterviewerSlot();
    interviewerSlotToUpdate.setId(1L);
    interviewerSlotToUpdate.setInterviewer(new User(UserRole.INTERVIEWER));

    when(coordinatorServiceMock.editSlot(1L, 1L, interviewerSlot)).thenReturn(interviewerSlot);
    InterviewerSlot resultSlot = coordinatorServiceMock.editSlot(1L, 1L, interviewerSlot);

    assertEquals(resultSlot, interviewerSlot);
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
    assertEquals(UserRole.INTERVIEWER, actualInterviewers.get(0).getRole());
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
    assertEquals(UserRole.COORDINATOR, actualCoordinators.get(0).getRole());
    assertSame(expectedCoordinators, actualCoordinators);
  }

  @Test
  void findById() {
    final User expectedUser = new User();
    when(coordinatorServiceMock.findById(anyLong())).thenReturn(expectedUser);

    final User actualUser = coordinatorServiceMock.findById(346642747L);

    assertNotNull(actualUser);
    assertEquals(expectedUser, actualUser);
  }

  @Test
  void getDashboardForWeek() {
    final Map<InterviewerSlot, Set<Long>> expectedInterviewerSlotsMap = new HashMap<>();
    final Map<CandidateSlot, Set<Long>> expectedCandidateSlotsMap = new HashMap<>();
    final Map<Long, Booking> expectedBookingsMap = new HashMap<>();

    when(interviewerService.getAllSlotsWithRelatedBookingIdsUsingWeekAndDay(anyInt(), any()))
        .thenReturn(expectedInterviewerSlotsMap);
    when(candidateService.getAllSlotsWithRelatedBookingIdsUsingDate(any()))
        .thenReturn(expectedCandidateSlotsMap);
    when(bookingService.getMapOfAllBookingsUsingDate(any()))
        .thenReturn(expectedBookingsMap);

    final DashboardDto result = coordinatorService.getDashboardForWeek(1);

    assertNotNull(result);
    assertEquals(result.getMonday().getBookingIdsByInterviewerSlot(), expectedInterviewerSlotsMap);
    assertEquals(result.getMonday().getBookingIdsByCandidateSlot(), expectedCandidateSlotsMap);
    assertEquals(result.getMonday().getBookingsByIds(), expectedBookingsMap);
  }
}