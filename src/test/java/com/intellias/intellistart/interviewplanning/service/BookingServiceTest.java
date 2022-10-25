package com.intellias.intellistart.interviewplanning.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import com.intellias.intellistart.interviewplanning.exception.BookingNotFoundException;
import com.intellias.intellistart.interviewplanning.model.Booking;
import com.intellias.intellistart.interviewplanning.model.CandidateSlot;
import com.intellias.intellistart.interviewplanning.model.InterviewerSlot;
import com.intellias.intellistart.interviewplanning.repository.BookingRepository;
import com.intellias.intellistart.interviewplanning.service.factory.BookingFactory;
import java.time.LocalDate;
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
}
