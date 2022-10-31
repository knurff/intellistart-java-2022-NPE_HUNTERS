package com.intellias.intellistart.interviewplanning.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import com.intellias.intellistart.interviewplanning.exception.BookingIsOverlappingException;
import com.intellias.intellistart.interviewplanning.exception.BookingLimitExceededException;
import com.intellias.intellistart.interviewplanning.exception.BookingNotFoundException;
import com.intellias.intellistart.interviewplanning.exception.InvalidBookingDurationException;
import com.intellias.intellistart.interviewplanning.exception.InvalidTimePeriodBoundaries;
import com.intellias.intellistart.interviewplanning.exception.SlotDatesAreNotEqualException;
import com.intellias.intellistart.interviewplanning.exception.SlotNotFoundException;
import com.intellias.intellistart.interviewplanning.model.Booking;
import com.intellias.intellistart.interviewplanning.model.CandidateSlot;
import com.intellias.intellistart.interviewplanning.model.InterviewerSlot;
import com.intellias.intellistart.interviewplanning.model.User;
import com.intellias.intellistart.interviewplanning.model.role.UserRole;
import com.intellias.intellistart.interviewplanning.repository.BookingRepository;
import com.intellias.intellistart.interviewplanning.repository.CandidateSlotRepository;
import com.intellias.intellistart.interviewplanning.repository.InterviewerSlotRepository;
import com.intellias.intellistart.interviewplanning.repository.UserRepository;
import com.intellias.intellistart.interviewplanning.service.factory.BookingFactory;
import com.intellias.intellistart.interviewplanning.service.factory.CandidateSlotFactory;
import com.intellias.intellistart.interviewplanning.service.factory.InterviewerSlotFactory;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class BookingServiceTest {

  @Mock
  private BookingRepository bookingRepository;
  @Mock
  private InterviewerSlotRepository interviewerSlotRepository;
  @Mock
  private CandidateSlotRepository candidateSlotRepository;
  @Mock
  private UserRepository userRepository;
  @InjectMocks
  private BookingService bookingService;

  @Test
  void getAllBookingIdsRelatedToInterviewerSlot() {
    final Booking expectedBooking = new Booking();
    expectedBooking.setId(354723168L);

    when(bookingRepository.getAllByInterviewerSlot(any())).thenReturn(List.of(expectedBooking));

    final Set<Long> expectedBookingIds = new HashSet<>();
    expectedBookingIds.add(expectedBooking.getId());

    final Set<Long> actualBookingIds =
        bookingService.getAllBookingIdsRelatedToInterviewerSlot(new InterviewerSlot());

    assertNotNull(actualBookingIds);
    assertEquals(expectedBookingIds, actualBookingIds);
  }

  @Test
  void getAllBookingIdsRelatedToCandidateSlot() {
    final Booking expectedBooking = new Booking();
    expectedBooking.setId(354723168L);

    when(bookingRepository.getAllByCandidateSlot(any())).thenReturn(List.of(expectedBooking));

    final Set<Long> expectedBookingIds = new HashSet<>();
    expectedBookingIds.add(expectedBooking.getId());

    final Set<Long> actualBookingIds =
        bookingService.getAllBookingIdsRelatedToCandidateSlot(new CandidateSlot());

    assertNotNull(actualBookingIds);
    assertEquals(expectedBookingIds, actualBookingIds);
  }

  @Test
  void getMapOfAllBookingsUsingDate() {
    final LocalDate dateParameter = LocalDate.now();

    final CandidateSlot candidateSlot = new CandidateSlot();
    candidateSlot.setDate(dateParameter);

    final Booking expectedBooking = new Booking();
    expectedBooking.setId(354723168L);
    expectedBooking.setCandidateSlot(candidateSlot);

    final Map<Long, Booking> expectedMap = new HashMap<>();
    expectedMap.put(expectedBooking.getId(), expectedBooking);

    when(bookingRepository.findAll()).thenReturn(List.of(expectedBooking));

    final Map<Long, Booking> actualMap = bookingService.getMapOfAllBookingsUsingDate(dateParameter);

    assertNotNull(actualMap);
    assertEquals(expectedMap, actualMap);
  }

  @Test
  void createBookingWorkingProperly() {
    Booking booking = BookingFactory.createBookingWithTimePeriod(LocalTime.of(16, 30),
        LocalTime.of(18, 0));

    createSlotsAndConfigureMockBehaviorForCreateBooking(1);
    when(bookingRepository.save(booking)).thenReturn(booking);

    assertNotNull(bookingService.createBooking(booking, 1L, 1L));
  }

  @Test
  void createBookingThrowsAnExceptionIfInterviewerSlotNotFound() {
    Booking booking = BookingFactory.createBooking();
    assertThrows(SlotNotFoundException.class,
        () -> bookingService.createBooking(booking, 1L, 1L));
  }

  @Test
  void createBookingThrowsAnExceptionIfCandidateSlotNotFound() {
    InterviewerSlot interviewerSlot = new InterviewerSlot();
    Booking booking = new Booking();

    when(interviewerSlotRepository.findById(1L)).thenReturn(Optional.of(interviewerSlot));

    assertThrows(SlotNotFoundException.class,
        () -> bookingService.createBooking(booking, 1L, 1L)
    );
  }

  @Test
  void createBookingThrowsAnExceptionIfSlotDatesAreNotEqual() {
    CandidateSlot candidateSlot = CandidateSlotFactory.createCandidateSlot();
    InterviewerSlot interviewerSlot = InterviewerSlotFactory.createInterviewerSlot();
    Booking booking = BookingFactory.createBooking();
    createInterviewerWithBookingLimitAndSetIntoSlot(interviewerSlot, 1);

    configureMockBehaviorForFindById(interviewerSlot, candidateSlot);

    assertThrows(SlotDatesAreNotEqualException.class, () ->
        bookingService.createBooking(booking, 1L, 1L));
  }

  @Test
  void createBookingThrowsAnExceptionIfPeriodIsNotInBoundariesOfSlotPeriod() {
    Booking booking1 = BookingFactory.createBookingWithTimePeriod(LocalTime.of(14, 30),
        LocalTime.of(16, 0));
    Booking booking2 = BookingFactory.createBookingWithTimePeriod(LocalTime.of(18, 0),
        LocalTime.of(19, 30));

    createSlotsAndConfigureMockBehaviorForCreateBooking(1);

    assertThrows(InvalidTimePeriodBoundaries.class,
        () -> bookingService.createBooking(booking1, 1L, 1L));
    assertThrows(InvalidTimePeriodBoundaries.class,
        () -> bookingService.createBooking(booking2, 1L, 1L));
  }

  @Test
  void createBookingThrowsAnExceptionIfOverlappingBookingExists() {
    Booking candidateSlotBooking = BookingFactory.createBookingWithTimePeriod(LocalTime.of(14, 30),
        LocalTime.of(16, 0));
    Booking interviewerSlotBooking = BookingFactory.createBookingWithTimePeriod
        (LocalTime.of(18, 0), LocalTime.of(19, 30));
    Booking booking1 = BookingFactory.createBookingWithTimePeriod(LocalTime.of(15, 0),
        LocalTime.of(16, 30));
    Booking booking2 = BookingFactory.createBookingWithTimePeriod(LocalTime.of(17, 30),
        LocalTime.of(19, 0));

    CandidateSlot candidateSlot = CandidateSlotFactory.createCandidateSlot();
    InterviewerSlot interviewerSlot = InterviewerSlotFactory.createInterviewerSlot();
    candidateSlot.setDate(interviewerSlot.getDate());

    candidateSlot.getBookings().add(candidateSlotBooking);
    interviewerSlot.getBookings().add(interviewerSlotBooking);
    createInterviewerWithBookingLimitAndSetIntoSlot(interviewerSlot, 1);

    configureMockBehaviorForFindById(interviewerSlot, candidateSlot);

    assertThrows(BookingIsOverlappingException.class,
        () -> bookingService.createBooking(booking1, 1L, 1L));
    assertThrows(BookingIsOverlappingException.class,
        () -> bookingService.createBooking(booking2, 1L, 1L));

  }

  @Test
  void createBookingThrowsAnExceptionIfEndTimeIsBeforeStartTime() {
    Booking booking = BookingFactory.createBookingWithTimePeriod(LocalTime.of(18, 0),
        LocalTime.of(16, 30));

    createSlotsAndConfigureMockBehaviorForCreateBooking(1);

    assertThrows(InvalidTimePeriodBoundaries.class, () -> bookingService.createBooking(booking,
        1L, 1L));
  }

  @Test
  void createBookingThrowsAnExceptionIfTimePeriodIsNotRounded() {
    Booking booking = BookingFactory.createBookingWithTimePeriod(LocalTime.of(16, 31),
        LocalTime.of(18, 1));

    createSlotsAndConfigureMockBehaviorForCreateBooking(1);

    assertThrows(InvalidTimePeriodBoundaries.class, () -> bookingService.createBooking(booking,
        1L, 1L));
  }

  @Test
  void createBookingThrowsAnExceptionIfTimePeriodDurationIsNotEqualToNinetyMinutes() {
    Booking booking = BookingFactory.createBookingWithTimePeriod(LocalTime.of(16, 30),
        LocalTime.of(18, 30));

    createSlotsAndConfigureMockBehaviorForCreateBooking(1);

    assertThrows(InvalidBookingDurationException.class, () -> bookingService.createBooking(booking,
        1L, 1L));
  }

  @Test
  void createBookingThrowsAnExceptionIfBookingLimitIsExceeded() {
    Booking booking = BookingFactory.createBookingWithTimePeriod(LocalTime.of(16, 30),
        LocalTime.of(18, 0));

    createSlotsAndConfigureMockBehaviorForCreateBooking(0);

    assertThrows(BookingLimitExceededException.class,
        () -> bookingService.createBooking(booking, 1L, 1L));
  }

  @Test
  void deleteBookingWorkingProperly() {
    Booking booking = BookingFactory.createBookingWithId();

    when(bookingRepository.findById(1L)).thenReturn(Optional.of(booking));

    assertTrue(bookingService.deleteBooking(1L));
  }

  @Test
  void deleteBookingThrowsAnExceptionIfBookingNotExists() {
    assertThrows(BookingNotFoundException.class, () ->
        bookingService.deleteBooking(1L));
  }

  @Test
  void updateBookingWorkingProperly() {
    Booking booking = BookingFactory.createBookingWithTimePeriod(LocalTime.of(16, 30),
        LocalTime.of(18, 0));

    createSlotsAndConfigureMockBehaviorForUpdateBooking(1);
    when(bookingRepository.save(booking)).thenReturn(booking);

    assertNotNull(bookingService.updateBooking(booking, 1L,1L, 1L));
  }

  @Test
  void updateBookingThrowsIfAnyOfAssociatedSlotsDoesNotExist() {
    Booking updatedBooking = new Booking();
    Long bookingId = 3536405697L;
    Long nonexistentSlotId = -1L;

    assertThrows(SlotNotFoundException.class, () -> {
        bookingService.updateBooking(
            updatedBooking,
            bookingId,
            nonexistentSlotId,
            nonexistentSlotId);}
    );
  }

  @Test
  void updateBookingThrowsAnExceptionIfTimePeriodDurationIsNotEqualToNinetyMinutes() {
    Booking booking = BookingFactory.createBookingWithTimePeriod(LocalTime.of(16, 30),
        LocalTime.of(18, 30));

    createSlotsAndConfigureMockBehaviorForUpdateBooking(1);

    assertThrows(InvalidBookingDurationException.class, () -> bookingService.updateBooking(booking,
        1L, 1L, 1L));
  }

  @Test
  void updateBookingThrowsAnExceptionIfTimePeriodIsNotRounded() {
    Booking booking = BookingFactory.createBookingWithTimePeriod(LocalTime.of(16, 31),
        LocalTime.of(18, 1));

    createSlotsAndConfigureMockBehaviorForUpdateBooking(1);

    assertThrows(InvalidTimePeriodBoundaries.class, () -> bookingService.updateBooking(booking,
        1L, 1L, 1L));
  }

  @Test
  void updateBookingThrowsAnExceptionIfEndTimeIsBeforeStartTime() {
    Booking booking = BookingFactory.createBookingWithTimePeriod(LocalTime.of(18, 0),
        LocalTime.of(16, 30));

    createSlotsAndConfigureMockBehaviorForUpdateBooking(1);

    assertThrows(InvalidTimePeriodBoundaries.class, () -> bookingService.updateBooking(booking,
        1L, 1L, 1L));
  }

  private void createSlotsAndConfigureMockBehaviorForCreateBooking(int bookingLimit) {
    CandidateSlot candidateSlot = CandidateSlotFactory.createCandidateSlot();
    InterviewerSlot interviewerSlot = InterviewerSlotFactory.createInterviewerSlot();
    candidateSlot.setDate(interviewerSlot.getDate());
    createInterviewerWithBookingLimitAndSetIntoSlot(interviewerSlot, bookingLimit);
    configureMockBehaviorForFindById(interviewerSlot, candidateSlot);
  }

  private void createSlotsAndConfigureMockBehaviorForUpdateBooking(int bookingLimit) {
    CandidateSlot candidateSlot = CandidateSlotFactory.createCandidateSlot();
    InterviewerSlot interviewerSlot = InterviewerSlotFactory.createInterviewerSlot();
    candidateSlot.setDate(interviewerSlot.getDate());
    configureMockBehaviorForFindById(interviewerSlot, candidateSlot);
  }

  private void createInterviewerWithBookingLimitAndSetIntoSlot(InterviewerSlot interviewerSlot, int
      bookingLimit) {
    User user = new User();
    user.setRole(UserRole.INTERVIEWER);
    user.setMaxBookingsPerWeek(bookingLimit);
    user.setId(1L);
    interviewerSlot.setInterviewer(user);

    when(userRepository.findById(1L)).thenReturn(Optional.of(user));
  }

  private void configureMockBehaviorForFindById(InterviewerSlot interviewerSlot,
      CandidateSlot candidateSlot) {
    when(interviewerSlotRepository.findById(1L)).thenReturn(Optional.of(interviewerSlot));
    when(candidateSlotRepository.findById(1L)).thenReturn(Optional.of(candidateSlot));
  }
}
