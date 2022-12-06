package com.intellias.intellistart.interviewplanning.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.when;

import com.intellias.intellistart.interviewplanning.controller.dto.DashboardDto;
import com.intellias.intellistart.interviewplanning.exception.*;
import com.intellias.intellistart.interviewplanning.model.*;
import com.intellias.intellistart.interviewplanning.model.role.UserRole;
import com.intellias.intellistart.interviewplanning.repository.InterviewerSlotRepository;
import com.intellias.intellistart.interviewplanning.repository.UserRepository;
import com.intellias.intellistart.interviewplanning.service.factory.InterviewerSlotFactory;
import com.intellias.intellistart.interviewplanning.service.factory.UserFactory;
import com.intellias.intellistart.interviewplanning.util.RequestParser;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class CoordinatorServiceTest {

  @Mock
  private InterviewerService interviewerService;
  @Mock
  private CandidateService candidateService;
  @Mock
  private BookingService bookingService;
  @Mock
  private UserRepository userRepository;
  @Mock
  private InterviewerSlotRepository interviewerSlotRepository;
  @InjectMocks
  private CoordinatorService coordinatorService;

  @Test
  void editSlotThrowsAnExceptionIfInterviewerNotFound() {
    Long interviewerId = 0L;
    Long interviewerSlotId = 0L;
    InterviewerSlot newSlot = new InterviewerSlot();

    when(userRepository.findById(interviewerId)).thenReturn(Optional.empty());

    assertThrows(InterviewerNotFoundException.class ,
            () -> coordinatorService.editSlot(interviewerId, interviewerSlotId, newSlot));
  }

  @Test
  void editSlotThrowsAnExceptionIfInterviewerSlotNotExists() {
    Long interviewerId = 0L;
    Long interviewerSlotId = 0L;
    InterviewerSlot newSlot = new InterviewerSlot();

    when(userRepository.findById(interviewerId)).thenReturn(Optional.of(new User()));
    when(interviewerService.findSlotByIdAndInterviewerId(interviewerSlotId, interviewerId))
            .thenThrow(SlotNotFoundException.class);

    assertThrows(SlotNotFoundException.class,
            () -> coordinatorService.editSlot(interviewerId, interviewerSlotId, newSlot));
  }

  @Test
  void editSlotThrowsAnExceptionIfOldSlotHasBookings() {
    Long interviewerId = 0L;
    Long interviewerSlotId = 0L;
    InterviewerSlot newSlot = new InterviewerSlot();

    InterviewerSlot oldSlot = new InterviewerSlot();
    oldSlot.getBookings().add(new Booking());

    when(userRepository.findById(interviewerId)).thenReturn(Optional.of(new User()));
    when(interviewerService.findSlotByIdAndInterviewerId(interviewerSlotId, interviewerId))
            .thenReturn(oldSlot);

    assertThrows(SlotContainsBookingsException.class,
            () -> coordinatorService.editSlot(interviewerId, interviewerSlotId, newSlot));
  }

  @Test
  void editSlotWorksProperly() {

    InterviewerSlot interviewerSlot = InterviewerSlotFactory.createInterviewerSlot();
    User interviewer = new User(UserRole.INTERVIEWER);

    InterviewerSlot interviewerSlotToUpdate = InterviewerSlotFactory.createAnotherInterviewerSlot();
    interviewerSlotToUpdate.setId(1L);
    interviewerSlotToUpdate.setInterviewer(interviewer);

    when(userRepository.findById(1L)).thenReturn(Optional.of(interviewer));
    when(interviewerService.findSlotByIdAndInterviewerId(1L, 1L))
            .thenReturn(interviewerSlotToUpdate);
    when(interviewerSlotRepository.getAllByInterviewer(interviewer))
            .thenReturn(List.of(interviewerSlotToUpdate));
    when(interviewerSlotRepository.save(interviewerSlot))
            .thenReturn(interviewerSlot);

    InterviewerSlot resultSlot = coordinatorService.editSlot(1L, 1L, interviewerSlot);

    assertEquals(resultSlot, interviewerSlot);
  }

  @Test
  void grantRoleForUserWorksProperly() {
    String email = "test@test.com";

    when(userRepository.getUserByEmail(email)).thenReturn(Optional.empty());

    try {
      User result = coordinatorService.grantRoleForUser(email, UserRole.COORDINATOR);
    } catch (Exception e) {
      fail("This method shouldn't throw an exception on given input");
    }
  }

  @Test
  void grantRoleForUserThrowsAnExceptionIfUserAlreadyHasRole() {
    User user = UserFactory.createCurrentCoordinator();
    String email = user.getEmail();

    when(userRepository.getUserByEmail(email)).thenReturn(Optional.of(user));

    assertThrows(UserAlreadyHasRoleException.class,
        () -> coordinatorService.grantRoleForUser(email, UserRole.INTERVIEWER));
  }

  @Test
  void revokeRoleFromUserThrowsAnExceptionIfUserWithoutRole() {
    Long customId = 0L;

    when(userRepository.getUserByIdAndRole(customId, UserRole.INTERVIEWER))
        .thenReturn(Optional.empty());

    assertThrows(NoRoleException.class,
        () -> coordinatorService.revokeRoleFromUser(customId, UserRole.INTERVIEWER));
  }

  @Test
  void revokeRoleFromUserWorksProperly() {
    User user = UserFactory.createCoordinator();
    Long id = user.getId();
    boolean result;

    when(userRepository.getUserByIdAndRole(id, UserRole.COORDINATOR))
        .thenReturn(Optional.of(user));

    try (MockedStatic<RequestParser> requestParserMock = mockStatic(RequestParser.class)) {
      requestParserMock.when(RequestParser::getUserEmailFromToken)
          .thenReturn(UserFactory.CURRENT_USER_EMAIL);

      result = coordinatorService.revokeRoleFromUser(id, UserRole.COORDINATOR);
    }

    assertTrue(result);
  }

  @Test
  void revokeRoleFromUserThrowsAnExceptionIfCoordinatorRevokesItself() {
    User user = UserFactory.createCurrentCoordinator();
    Long id = user.getId();

    when(userRepository.getUserByIdAndRole(id, UserRole.COORDINATOR))
        .thenReturn(Optional.of(user));

    try (MockedStatic<RequestParser> requestParserMock = mockStatic(RequestParser.class)) {
      requestParserMock.when(RequestParser::getUserEmailFromToken)
          .thenReturn(UserFactory.CURRENT_USER_EMAIL);

      assertThrows(SelfRevokingException.class,
          () -> coordinatorService.revokeRoleFromUser(id, UserRole.COORDINATOR));
    }
  }

  @Test
  void getAllInterviewers() {
    final List<User> expectedInterviewers = List.of(new User(UserRole.INTERVIEWER));

    when(userRepository.getAllByRole(UserRole.INTERVIEWER))
            .thenReturn(expectedInterviewers);

    final List<User> actualInterviewers = coordinatorService.getAllInterviewers();

    assertNotNull(actualInterviewers);
    assertEquals(expectedInterviewers.size(), actualInterviewers.size());
    assertEquals(UserRole.INTERVIEWER, actualInterviewers.get(0).getRole());
    assertSame(expectedInterviewers, actualInterviewers);
  }

  @Test
  void getAllCoordinators() {
    final List<User> expectedCoordinators = List.of(new User(UserRole.COORDINATOR));

    when(userRepository.getAllByRole(UserRole.COORDINATOR))
            .thenReturn(expectedCoordinators);

    final List<User> actualCoordinators = coordinatorService.getAllCoordinators();

    assertNotNull(actualCoordinators);
    assertEquals(expectedCoordinators.size(), actualCoordinators.size());
    assertEquals(UserRole.COORDINATOR, actualCoordinators.get(0).getRole());
    assertSame(expectedCoordinators, actualCoordinators);
  }

  @Test
  void findById() {
    final User expectedUser = new User();

    when(userRepository.findById(anyLong())).thenReturn(Optional.of(expectedUser));

    final User actualUser = coordinatorService.findById(346642747L);

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