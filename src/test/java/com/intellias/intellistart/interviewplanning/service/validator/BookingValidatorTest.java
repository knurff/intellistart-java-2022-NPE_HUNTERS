package com.intellias.intellistart.interviewplanning.service.validator;

import static org.junit.jupiter.api.Assertions.*;

import com.intellias.intellistart.interviewplanning.exception.BookingIsOverlappingException;
import com.intellias.intellistart.interviewplanning.exception.InvalidTimePeriodBoundaries;
import com.intellias.intellistart.interviewplanning.exception.SlotDatesAreNotEqualException;
import com.intellias.intellistart.interviewplanning.model.Booking;
import com.intellias.intellistart.interviewplanning.model.CandidateSlot;
import com.intellias.intellistart.interviewplanning.model.InterviewerSlot;
import com.intellias.intellistart.interviewplanning.model.TimePeriod;
import com.intellias.intellistart.interviewplanning.service.factory.CandidateSlotFactory;
import com.intellias.intellistart.interviewplanning.service.factory.InterviewerSlotFactory;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class BookingValidatorTest {

  @Test
  void validateThrowsAnExceptionIfSlotsDatesAreNotEqual() {
    CandidateSlot candidateSlot = CandidateSlotFactory.createSlotForBookingValidator();
    LocalDate candidateDate = candidateSlot.getDate().plusDays(1L);
    candidateSlot.setDate(candidateDate);

    InterviewerSlot interviewerSlot =
        InterviewerSlotFactory.createInterviewerSlotForBookingValidator();

    int interviewerWeek = interviewerSlot.getWeek() + 1;
    interviewerSlot.setWeek(interviewerWeek);

    Booking booking = new Booking();
    booking.setCandidateSlot(candidateSlot);
    booking.setInterviewerSlot(interviewerSlot);

    String expectedMessage = "Candidate slot date and interviewer slot date are not equal";

    Exception e = assertThrows(SlotDatesAreNotEqualException.class,
        () -> BookingValidator.validate(booking));

    assertEquals(expectedMessage, e.getMessage());
  }

  @Test
  void validateThrowsAnExceptionIfBookingInvolvesCandidateSlot() {
    CandidateSlot candidateSlot = CandidateSlotFactory.createSlotForBookingValidator();

    InterviewerSlot interviewerSlot =
        InterviewerSlotFactory.createInterviewerSlotForBookingValidator();

    TimePeriod bookingPeriod = new TimePeriod(
        LocalTime.of(11, 0),
        LocalTime.of(12, 30)
    );

    Booking booking = new Booking();
    booking.setCandidateSlot(candidateSlot);
    booking.setInterviewerSlot(interviewerSlot);
    booking.setPeriod(bookingPeriod);

    String expectedMessage = "Booking time must be in edges of candidate slot time";

    Exception e = assertThrows(InvalidTimePeriodBoundaries.class,
        () -> BookingValidator.validate(booking));

    assertEquals(expectedMessage, e.getMessage());
  }

  @Test
  void validateThrowsAnExceptionIfBookingInvolvesInterviewerSlot() {
    CandidateSlot candidateSlot = CandidateSlotFactory.createSlotForBookingValidator();

    InterviewerSlot interviewerSlot =
        InterviewerSlotFactory.createInterviewerSlotForBookingValidator();

    interviewerSlot.getPeriod().setEndTime(LocalTime.of(14, 0));

    TimePeriod bookingPeriod = new TimePeriod(
        LocalTime.of(13, 0),
        LocalTime.of(14, 30)
    );

    Booking booking = new Booking();
    booking.setCandidateSlot(candidateSlot);
    booking.setInterviewerSlot(interviewerSlot);
    booking.setPeriod(bookingPeriod);

    String expectedMessage = "Booking time must be in edges of interviewer slot time";

    Exception e = assertThrows(InvalidTimePeriodBoundaries.class,
        () -> BookingValidator.validate(booking));

    assertEquals(expectedMessage, e.getMessage());
  }

  @Test
  void validateThrowsAnExceptionIfBookingIsOverlappingAnotherCandidateBooking() {
    CandidateSlot candidateSlot = CandidateSlotFactory.createSlotForBookingValidator();

    InterviewerSlot interviewerSlot =
        InterviewerSlotFactory.createInterviewerSlotForBookingValidator();

    TimePeriod bookingPeriod = new TimePeriod(
        LocalTime.of(13, 0),
        LocalTime.of(14, 30)
    );

    TimePeriod existingBookingPeriod = new TimePeriod(
        LocalTime.of(12, 0),
        LocalTime.of(13, 30)
    );

    Set<Booking> candidateBookings = new HashSet<>();
    candidateBookings.add(new Booking(existingBookingPeriod));
    candidateSlot.setBookings(candidateBookings);

    Booking booking = new Booking();
    booking.setCandidateSlot(candidateSlot);
    booking.setInterviewerSlot(interviewerSlot);
    booking.setPeriod(bookingPeriod);

    String expectedMessage = "This booking is overlapping another booking";

    Exception e = assertThrows(BookingIsOverlappingException.class,
        () -> BookingValidator.validate(booking));

    assertEquals(expectedMessage, e.getMessage());
  }

  @Test
  void validateThrowsAnExceptionIfBookingIsOverlappingAnotherInterviewerBooking() {
    CandidateSlot candidateSlot = CandidateSlotFactory.createSlotForBookingValidator();

    InterviewerSlot interviewerSlot =
        InterviewerSlotFactory.createInterviewerSlotForBookingValidator();

    TimePeriod bookingPeriod = new TimePeriod(
        LocalTime.of(13, 0),
        LocalTime.of(14, 30)
    );

    TimePeriod existingBookingPeriod = new TimePeriod(
        LocalTime.of(12, 0),
        LocalTime.of(13, 30)
    );

    Set<Booking> interviewerBookings = new HashSet<>();
    interviewerBookings.add(new Booking(existingBookingPeriod));
    interviewerSlot.setBookings(interviewerBookings);

    Booking booking = new Booking();
    booking.setCandidateSlot(candidateSlot);
    booking.setInterviewerSlot(interviewerSlot);
    booking.setPeriod(bookingPeriod);

    String expectedMessage = "This booking is overlapping another booking";

    Exception e = assertThrows(BookingIsOverlappingException.class,
        () -> BookingValidator.validate(booking));

    assertEquals(expectedMessage, e.getMessage());
  }

  @Test
  void validateWorksProperly() {
    CandidateSlot candidateSlot = CandidateSlotFactory.createSlotForBookingValidator();

    InterviewerSlot interviewerSlot =
        InterviewerSlotFactory.createInterviewerSlotForBookingValidator();

    TimePeriod bookingPeriod = new TimePeriod(
        LocalTime.of(13, 0),
        LocalTime.of(14, 30)
    );

    Booking booking = new Booking();
    booking.setCandidateSlot(candidateSlot);
    booking.setInterviewerSlot(interviewerSlot);
    booking.setPeriod(bookingPeriod);

    try {
      BookingValidator.validate(booking);
    } catch (Exception e) {
      fail("This method should not throw an exception on given input");
    }
  }
}