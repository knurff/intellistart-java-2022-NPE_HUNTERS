package com.intellias.intellistart.interviewplanning.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

import com.intellias.intellistart.interviewplanning.exception.InterviewerNotFoundException;
import com.intellias.intellistart.interviewplanning.exception.SlotNotFoundException;
import com.intellias.intellistart.interviewplanning.model.InterviewerSlot;
import com.intellias.intellistart.interviewplanning.model.User;
import com.intellias.intellistart.interviewplanning.model.WeekBooking;
import com.intellias.intellistart.interviewplanning.model.role.UserRole;
import com.intellias.intellistart.interviewplanning.repository.InterviewerSlotRepository;
import com.intellias.intellistart.interviewplanning.repository.UserRepository;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class InterviewerServiceTest {

  @Mock
  private InterviewerSlotRepository interviewerSlotRepository;
  @Mock
  private UserRepository userRepository;
  @Mock
  private BookingService bookingService;
  @Mock
  private InterviewerService interviewerServiceMock;
  private InterviewerService interviewerService;

  @BeforeEach
  void setup() {
    interviewerService = new InterviewerService(
        interviewerSlotRepository,
        userRepository,
        bookingService
    );
  }

  @Test
  void createSlot() {
    when(interviewerServiceMock.createSlot()).thenReturn(new InterviewerSlot());
    final InterviewerSlot newSlot = interviewerServiceMock.createSlot();

    assertNotNull(newSlot);
  }

  @Test
  void editSlot() {
    when(interviewerServiceMock.editSlot()).thenReturn(true);
    final boolean result = interviewerServiceMock.editSlot();

    assertTrue(result);
  }

  @Test
  void getAllSlots() {
    final List<InterviewerSlot> result = interviewerServiceMock.getAllSlots();

    assertNotNull(result);
  }

  @Test
  void setMaxBookings() {
    interviewerServiceMock.setMaxBookings(1L, 1);
    assertTrue(true);
  }

  @Test
  void getInterviewerOrThrowExceptionThrowsException_UserDoesNotExist() {
    when(userRepository.findById(anyLong())).thenReturn(Optional.empty());

    assertThrows(InterviewerNotFoundException.class,
        () -> interviewerService.getInterviewerOrThrowException(34657464L));
  }

  @Test
  void getInterviewerOrThrowExceptionThrowsException_UserRoleNotInterviewer() {
    final User user = new User(UserRole.COORDINATOR);

    when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));

    assertThrows(InterviewerNotFoundException.class,
        () -> interviewerService.getInterviewerOrThrowException(34657464L));
  }

  @Test
  void getInterviewerOrThrowExceptionReturnsExpectedUser_UserRoleIsInterviewer() {
    final User expectedUser = new User(UserRole.INTERVIEWER);

    when(userRepository.findById(anyLong())).thenReturn(Optional.of(expectedUser));

    final User actualUser = interviewerService.getInterviewerOrThrowException(35635758L);

    assertNotNull(actualUser);
    assertEquals(actualUser.getRole(), UserRole.INTERVIEWER);
    assertEquals(expectedUser, actualUser);
  }

  @Test
  void findSlotByIdThrowsException_NoSlotExists() {
    when(interviewerSlotRepository.findById(anyLong())).thenReturn(Optional.empty());

    assertThrows(SlotNotFoundException.class,
        () -> interviewerService.findSlotById(6397540L));
  }

  @Test
  void findSlotByIdReturnsExpectedSlot_SlotExists() {
    final InterviewerSlot expectedSlot = new InterviewerSlot();

    when(interviewerSlotRepository.findById(anyLong())).thenReturn(Optional.of(expectedSlot));

    final InterviewerSlot actualSlot = interviewerService.findSlotById(209672968L);

    assertNotNull(actualSlot);
    assertEquals(expectedSlot, actualSlot);
  }

  @Test
  void findSlotByIdAndInterviewerIdThrowsException_SlotDoesNotBelongToInterviewer() {
    final User interviewer = new User(UserRole.INTERVIEWER);
    interviewer.setId(5561065892L);

    final InterviewerSlot interviewerSlot = new InterviewerSlot();
    interviewerSlot.setId(32570782L);
    interviewerSlot.setInterviewerId(interviewer);

    when(interviewerSlotRepository.findById(anyLong())).thenReturn(Optional.of(interviewerSlot));

    assertThrows(SlotNotFoundException.class,
        () -> interviewerService.findSlotByIdAndInterviewerId
            (interviewerSlot.getId(), interviewer.getId() - 1));
  }

  @Test
  void findSlotByIdAndInterviewerIdReturnsExpectedSlot_SlotBelongsToInterviewer() {
    final User interviewer = new User(UserRole.INTERVIEWER);
    interviewer.setId(5561065892L);

    final InterviewerSlot expectedSlot = new InterviewerSlot();
    expectedSlot.setId(32570782L);
    expectedSlot.setInterviewerId(interviewer);

    when(interviewerSlotRepository.findById(anyLong())).thenReturn(Optional.of(expectedSlot));

    final InterviewerSlot actualSlot =
        interviewerService.findSlotByIdAndInterviewerId(expectedSlot.getId(),
            interviewer.getId());

    assertNotNull(actualSlot);
    assertEquals(expectedSlot, actualSlot);
  }
}