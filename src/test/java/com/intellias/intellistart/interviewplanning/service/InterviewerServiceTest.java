package com.intellias.intellistart.interviewplanning.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.when;
import com.intellias.intellistart.interviewplanning.exception.InterviewerNotFoundException;
import com.intellias.intellistart.interviewplanning.exception.InvalidDayForSlotCreationException;
import com.intellias.intellistart.interviewplanning.exception.InvalidSlotDateException;
import com.intellias.intellistart.interviewplanning.exception.InvalidTimePeriodBoundaries;
import com.intellias.intellistart.interviewplanning.exception.SlotIsOverlappingException;
import com.intellias.intellistart.interviewplanning.exception.SlotNotFoundException;
import com.intellias.intellistart.interviewplanning.model.InterviewerSlot;
import com.intellias.intellistart.interviewplanning.model.User;
import com.intellias.intellistart.interviewplanning.model.WeekBooking;
import com.intellias.intellistart.interviewplanning.model.role.UserRole;
import com.intellias.intellistart.interviewplanning.repository.InterviewerSlotRepository;
import com.intellias.intellistart.interviewplanning.repository.UserRepository;
import com.intellias.intellistart.interviewplanning.service.factory.InterviewerSlotFactory;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class InterviewerServiceTest {

  @Mock
  private InterviewerSlotRepository interviewerSlotRepository;
  @Mock
  private UserRepository userRepository;
  @Mock
  private InterviewerService interviewerServiceMock;
  @InjectMocks
  private InterviewerService interviewerService;

  private static MockedStatic<LocalDate> localDateMock;

  @BeforeAll
  static void setUp() {
    localDateMock = mockStatic(LocalDate.class,
        Mockito.CALLS_REAL_METHODS);

  }

  @Test
  void createSlotWorkingProperly() {
    InterviewerSlot interviewerSlot = InterviewerSlotFactory.createInterviewerSlot();

    setInterviewerIntoSlotAndConfigureMock(interviewerSlot);
    setValidDateIfNeeded();
    when(interviewerSlotRepository.save(interviewerSlot)).thenReturn(interviewerSlot);

    assertNotNull(interviewerService.createSlot(interviewerSlot, 1L));
    localDateMock.clearInvocations();
  }


  @Test
  void createSlotThrowsAnExceptionIfInterviewerNotFound() {
    InterviewerSlot interviewerSlot = InterviewerSlotFactory.createInterviewerSlot();

    assertThrows(InterviewerNotFoundException.class,
        () -> interviewerService.createSlot(interviewerSlot, 1L));
  }


  @Test
  void createSlotThrowsAnExceptionIfDateIsNotInFuture() {
    InterviewerSlot interviewerSlot = InterviewerSlotFactory.createSlotWithDateInPast();

    setInterviewerIntoSlotAndConfigureMock(interviewerSlot);

    assertThrows(InvalidSlotDateException.class,
        () -> interviewerService.createSlot(interviewerSlot, 1L));
  }

  @Test
  void createSlotThrowsAnExceptionIfTimePeriodIsNotInWorkingHours() {
    InterviewerSlot interviewerSlot = InterviewerSlotFactory.
        createSlotWithTimePeriodInNotWorkingHours();

    setInterviewerIntoSlotAndConfigureMock(interviewerSlot);

    assertThrows(InvalidTimePeriodBoundaries.class,
        () -> interviewerService.createSlot(interviewerSlot, 1L));
  }

  @Test
  void createSlotThrowsAnExceptionIfDurationLessThanMin() {
    InterviewerSlot interviewerSlot = InterviewerSlotFactory.createSlotWithDurationLessThanMin();

    setInterviewerIntoSlotAndConfigureMock(interviewerSlot);

    assertThrows(InvalidTimePeriodBoundaries.class,
        () -> interviewerService.createSlot(interviewerSlot, 1L));

  }

  @Test
  void createSlotThrowsAnExceptionIfPeriodIsNotRounded() {
    InterviewerSlot interviewerSlot = InterviewerSlotFactory.createSlotWithNotRoundedPeriod();

    setInterviewerIntoSlotAndConfigureMock(interviewerSlot);

    assertThrows(InvalidTimePeriodBoundaries.class,
        () -> interviewerService.createSlot(interviewerSlot, 1L));
  }

  @Test
  void createSlotThrowsAnExceptionIfAnOverlappingSlotExists() {
    InterviewerSlot interviewerSlot = InterviewerSlotFactory.createInterviewerSlot();
    InterviewerSlot overlappingSlot = InterviewerSlotFactory.createInterviewerSlot();

    setInterviewerIntoSlotAndConfigureMock(interviewerSlot);
    when(interviewerSlotRepository.getAllByInterviewer(
        interviewerSlot.getInterviewer())).thenReturn(List.of(overlappingSlot));

    assertThrows(SlotIsOverlappingException.class,
        () -> interviewerService.createSlot(interviewerSlot, 1L));
  }

  @Test
  void createSlotThrowsAnExceptionIfSlotDateIsNotOnTheNextWeek() {
    InterviewerSlot interviewerSlot = InterviewerSlotFactory.createSlotWithDateNotOnTheNextWeek();

    setInterviewerIntoSlotAndConfigureMock(interviewerSlot);

    assertThrows(InvalidSlotDateException.class,
        () -> interviewerService.createSlot(interviewerSlot, 1L));
  }

  @Test
  void createSlotThrowsAnExceptionIfSlotIsCreatedAfterEndOfCurrentFriday() {
    InterviewerSlot interviewerSlot = InterviewerSlotFactory.createInterviewerSlot();

    setInterviewerIntoSlotAndConfigureMock(interviewerSlot);
    setInvalidDateIfNeeded();

    assertThrows(InvalidDayForSlotCreationException.class,
        () -> interviewerService.createSlot(interviewerSlot, 1L));
    localDateMock.clearInvocations();
  }

  @Test
  void editSlotWorkingProperly() {
    InterviewerSlot interviewerSlotToUpdate = InterviewerSlotFactory.createInterviewerSlot();
    InterviewerSlot interviewerSlot = InterviewerSlotFactory.createInterviewerSlot();
    interviewerSlot.setDayOfWeek(DayOfWeek.FRIDAY);

    prepareInterviewerAndConfigureMockBehaviorForEditSlot(interviewerSlot);
    setValidDateIfNeeded();
    when(interviewerSlotRepository.save(interviewerSlot)).thenReturn(interviewerSlot);
    when(interviewerSlotRepository.findById(1L)).thenReturn(Optional.of(interviewerSlotToUpdate)
    );

    InterviewerSlot updatedInterviewerSlot =
        interviewerService.editSlot(interviewerSlot, 1L, 1L);
    assertNotNull(updatedInterviewerSlot);
    assertEquals(DayOfWeek.FRIDAY, updatedInterviewerSlot.getDayOfWeek());
    localDateMock.clearInvocations();
  }

  @Test
  void editSlotThrowsAnExceptionIfSlotNotFound() {
    InterviewerSlot interviewerSlot = InterviewerSlotFactory.createInterviewerSlot();

    assertThrows(SlotNotFoundException.class,
        () -> interviewerService.editSlot(interviewerSlot, 1L, 1L));
  }

  @Test
  void editSlotThrowsAnExceptionIfInterviewerNotFound() {
    InterviewerSlot interviewerSlot = InterviewerSlotFactory.createInterviewerSlot();

    when(interviewerSlotRepository.findById(1L)).thenReturn(Optional.of(interviewerSlot));

    assertThrows(InterviewerNotFoundException.class,
        () -> interviewerService.editSlot(interviewerSlot, 1L, 1L));
  }

  @Test
  void editSlotThrowsAnExceptionIfDateIsNotInFuture() {
    InterviewerSlot interviewerSlot = InterviewerSlotFactory.createSlotWithDateInPast();

    prepareInterviewerAndConfigureMockBehaviorForEditSlot(interviewerSlot);

    assertThrows(InvalidSlotDateException.class,
        () -> interviewerService.editSlot(interviewerSlot, 1L, 1L));
  }

  @Test
  void editSlotThrowsAnExceptionIfTimePeriodIsNotInWorkingHours() {
    InterviewerSlot interviewerSlot = InterviewerSlotFactory
        .createSlotWithTimePeriodInNotWorkingHours();

    prepareInterviewerAndConfigureMockBehaviorForEditSlot(interviewerSlot);

    assertThrows(InvalidTimePeriodBoundaries.class,
        () -> interviewerService.editSlot(interviewerSlot, 1L, 1L));
  }

  @Test
  void editSlotThrowsAnExceptionIfDurationLessThanMin() {
    InterviewerSlot interviewerSlot = InterviewerSlotFactory.createSlotWithDurationLessThanMin();

    prepareInterviewerAndConfigureMockBehaviorForEditSlot(interviewerSlot);

    assertThrows(InvalidTimePeriodBoundaries.class,
        () -> interviewerService.editSlot(interviewerSlot, 1L, 1L));
  }

  @Test
  void editSlotThrowsAnExceptionIfPeriodIsNotRounded() {
    InterviewerSlot interviewerSlot = InterviewerSlotFactory.createSlotWithNotRoundedPeriod();

    prepareInterviewerAndConfigureMockBehaviorForEditSlot(interviewerSlot);

    assertThrows(InvalidTimePeriodBoundaries.class,
        () -> interviewerService.editSlot(interviewerSlot, 1L, 1L));
  }

  @Test
  void editSlotThrowsAnExceptionIfAnOverlappingSlotExists() {
    InterviewerSlot interviewerSlot = InterviewerSlotFactory.createInterviewerSlot();
    InterviewerSlot overlappingSlot = InterviewerSlotFactory.createInterviewerSlot();
    InterviewerSlot slotToUpdate = InterviewerSlotFactory.createInterviewerSlot();

    prepareInterviewerAndConfigureMockBehaviorForEditSlot(interviewerSlot);
    when(interviewerSlotRepository.getAllByInterviewer(
        interviewerSlot.getInterviewer())).thenReturn(List.of(overlappingSlot, slotToUpdate));

    assertThrows(SlotIsOverlappingException.class,
        () -> interviewerService.editSlot(interviewerSlot, 1L, 1L));
  }

  @Test
  void editSlotThrowsAnExceptionIfSlotDateIsNotOnTheNextWeek() {
    InterviewerSlot interviewerSlot = InterviewerSlotFactory.createSlotWithDateNotOnTheNextWeek();

    prepareInterviewerAndConfigureMockBehaviorForEditSlot(interviewerSlot);

    assertThrows(InvalidSlotDateException.class,
        () -> interviewerService.editSlot(interviewerSlot, 1L, 1L));
  }

  @Test
  void editSlotThrowsAnExceptionIfSlotIsCreatedAfterEndOfCurrentFriday() {
    InterviewerSlot interviewerSlot = InterviewerSlotFactory.createInterviewerSlot();

    prepareInterviewerAndConfigureMockBehaviorForEditSlot(interviewerSlot);
    setInvalidDateIfNeeded();

    assertThrows(InvalidDayForSlotCreationException.class,
        () -> interviewerService.editSlot(interviewerSlot, 1L, 1L));
    localDateMock.clearInvocations();
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
  void getInterviewerOrThrowExceptionThrowsExceptionIfUserDoesNotExist() {
    when(userRepository.findById(anyLong())).thenReturn(Optional.empty());

    assertThrows(InterviewerNotFoundException.class,
        () -> interviewerService.getInterviewerOrThrowException(34657464L));
  }

  @Test
  void getInterviewerOrThrowExceptionThrowsExceptionIfUserRoleNotInterviewer() {
    final User user = new User(UserRole.COORDINATOR);

    when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));

    assertThrows(InterviewerNotFoundException.class,
        () -> interviewerService.getInterviewerOrThrowException(34657464L));
  }

  @Test
  void getInterviewerOrThrowExceptionReturnsExpectedUserIfUserRoleIsInterviewer() {
    final User expectedUser = new User(UserRole.INTERVIEWER);

    when(userRepository.findById(anyLong())).thenReturn(Optional.of(expectedUser));

    final User actualUser = interviewerService.getInterviewerOrThrowException(35635758L);

    assertNotNull(actualUser);
    assertEquals(UserRole.INTERVIEWER, actualUser.getRole());
    assertEquals(expectedUser, actualUser);
  }

  @Test
  void findSlotByIdThrowsExceptionIfNoSlotExists() {
    when(interviewerSlotRepository.findById(anyLong())).thenReturn(Optional.empty());

    assertThrows(SlotNotFoundException.class,
        () -> interviewerService.findSlotById(6397540L));
  }

  @Test
  void findSlotByIdReturnsExpectedSlotIfSlotExists() {
    final InterviewerSlot expectedSlot = new InterviewerSlot();

    when(interviewerSlotRepository.findById(anyLong())).thenReturn(Optional.of(expectedSlot));

    final InterviewerSlot actualSlot = interviewerService.findSlotById(209672968L);

    assertNotNull(actualSlot);
    assertEquals(expectedSlot, actualSlot);
  }

  @Test
  void findSlotByIdAndInterviewerIdThrowsExceptionIfSlotDoesNotBelongToInterviewer() {
    final User interviewer = new User(UserRole.INTERVIEWER);
    interviewer.setId(5561065892L);

    final InterviewerSlot interviewerSlot = new InterviewerSlot();
    interviewerSlot.setId(32570782L);
    interviewerSlot.setInterviewer(interviewer);

    when(interviewerSlotRepository.findById(anyLong())).thenReturn(Optional.of(interviewerSlot));

    assertThrows(SlotNotFoundException.class,
        () -> interviewerService.findSlotByIdAndInterviewerId
            (interviewerSlot.getId(), interviewer.getId() - 1));
  }

  @Test
  void findSlotByIdAndInterviewerIdReturnsExpectedSlotIfSlotBelongsToInterviewer() {
    final User interviewer = new User(UserRole.INTERVIEWER);
    interviewer.setId(5561065892L);

    final InterviewerSlot expectedSlot = new InterviewerSlot();
    expectedSlot.setId(32570782L);
    expectedSlot.setInterviewer(interviewer);

    when(interviewerSlotRepository.findById(anyLong())).thenReturn(Optional.of(expectedSlot));

    final InterviewerSlot actualSlot =
        interviewerService.findSlotByIdAndInterviewerId(expectedSlot.getId(),
            interviewer.getId());

    assertNotNull(actualSlot);
    assertEquals(expectedSlot, actualSlot);
  }

  private void prepareInterviewerAndConfigureMockBehaviorForEditSlot(
      InterviewerSlot interviewerSlot) {
    setInterviewerIntoSlotAndConfigureMock(interviewerSlot);
    when(interviewerSlotRepository.findById(1L)).thenReturn(Optional.of(interviewerSlot));
  }

  private void setInterviewerIntoSlotAndConfigureMock(InterviewerSlot interviewerSlot) {
    User interviewer = new User();
    interviewer.setRole(UserRole.INTERVIEWER);
    interviewerSlot.setInterviewer(interviewer);

    when(userRepository.findById(1L)).thenReturn(Optional.of(interviewer));
  }

  private void setValidDateIfNeeded() {
    if (LocalDate.now().getDayOfWeek().getValue() > 5) {
      LocalDate validDate = LocalDate.now().minusDays(3);
      localDateMock.when(LocalDate::now).thenReturn(validDate);
    }
  }

  private void setInvalidDateIfNeeded() {
    int numberOfCurrentDayOfWeek = LocalDate.now().getDayOfWeek().getValue();
    if (numberOfCurrentDayOfWeek < 6) {
      LocalDate invalidDate = LocalDate.now().plusDays(6 - numberOfCurrentDayOfWeek);
      localDateMock.when(LocalDate::now).thenReturn(invalidDate);
    }
  }
}
