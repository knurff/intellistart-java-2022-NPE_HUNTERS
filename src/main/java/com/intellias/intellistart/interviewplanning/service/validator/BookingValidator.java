package com.intellias.intellistart.interviewplanning.service.validator;

import com.intellias.intellistart.interviewplanning.exception.BookingIsOverlappingException;
import com.intellias.intellistart.interviewplanning.exception.InvalidTimePeriodBoundaries;
import com.intellias.intellistart.interviewplanning.exception.SlotDatesAreNotEqualException;
import com.intellias.intellistart.interviewplanning.model.Booking;
import com.intellias.intellistart.interviewplanning.model.TimePeriod;
import java.util.Set;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * BookingValidator class, which contains methods to validate booking.
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class BookingValidator {

  /**
   * Method, that validates equality of slot dates, correct time boundaries that involves time
   * period of slots, absence of overlapping with another bookings and time period boundaries.
   */
  public static void validate(Booking booking) {
    checkThatSlotsDateAreEqual(booking);
    checkThatBookingPeriodInvolvesSlotsTime(booking);

    Set<Booking> candidateSlotBookings = booking.getCandidateSlot().getBookings();
    Set<Booking> interviewerSlotBookings = booking.getInterviewerSlot().getBookings();
    checkThatBookingIsNotOverlappingAnotherBooking(booking, candidateSlotBookings);
    checkThatBookingIsNotOverlappingAnotherBooking(booking, interviewerSlotBookings);

    TimePeriodValidator.checkTimePeriodWithoutWorkingHours(booking.getPeriod());
  }

  private static void checkThatBookingPeriodInvolvesSlotsTime(Booking booking) {
    TimePeriod candidateTimePeriod = booking.getCandidateSlot().getPeriod();
    TimePeriod interviewerTimePeriod = booking.getInterviewerSlot().getPeriod();
    TimePeriod bookingTimePeriod = booking.getPeriod();

    if (bookingTimePeriod.getStartTime().isBefore(candidateTimePeriod.getStartTime())
        || bookingTimePeriod.getEndTime().isAfter(candidateTimePeriod.getEndTime())) {
      throw new InvalidTimePeriodBoundaries("Booking time must be in edges of candidate slot time");
    } else if (bookingTimePeriod.getStartTime().isBefore(interviewerTimePeriod.getStartTime())
        || bookingTimePeriod.getEndTime().isAfter(interviewerTimePeriod.getEndTime())) {
      throw new InvalidTimePeriodBoundaries(
          "Booking time must be in edges of interviewer slot time");
    }
  }

  private static void checkThatBookingIsNotOverlappingAnotherBooking(Booking booking,
      Set<Booking> slotBookings) {
    for (Booking createdBooking : slotBookings) {
      if (TimePeriodValidator.isOverlapping(booking.getPeriod(), createdBooking.getPeriod())) {
        throw new BookingIsOverlappingException("This booking is overlapping another booking");
      }
    }
  }

  private static void checkThatSlotsDateAreEqual(Booking booking) {
    if (!booking.getCandidateSlot().getDate().equals(booking.getInterviewerSlot().getDate())) {
      throw new SlotDatesAreNotEqualException(
          "Candidate slot date and interviewer slot date are not equal");
    }

  }
}
