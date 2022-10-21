package com.intellias.intellistart.interviewplanning.service;

import com.intellias.intellistart.interviewplanning.model.Booking;
import com.intellias.intellistart.interviewplanning.model.CandidateSlot;
import com.intellias.intellistart.interviewplanning.model.InterviewerSlot;
import com.intellias.intellistart.interviewplanning.repository.BookingRepository;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * BookingService service.
 */
@Service
@AllArgsConstructor
public class BookingService {
  private final BookingRepository bookingRepository;

  public Set<Long> getAllBookingIdsRelatedToInterviewerSlot(final InterviewerSlot interviewerSlot) {
    final List<Booking> relatedBookings = bookingRepository.getAllByInterviewerSlot(interviewerSlot);
    final Set<Long> result = new HashSet<>();

    for (final Booking relatedBooking: relatedBookings) {
      result.add(relatedBooking.getId());
    }

    return result;
  }

  public Set<Long> getAllBookingIdsRelatedToCandidateSlot(final CandidateSlot candidateSlot) {
    final List<Booking> relatedBookings = bookingRepository.getAllByCandidateSlot(candidateSlot);
    final Set<Long> result = new HashSet<>();

    for (final Booking relatedBooking: relatedBookings) {
      result.add(relatedBooking.getId());
    }

    return result;
  }

  public Map<Long, Booking> getMapOfAllBookingsUsingDate(final LocalDate localDate) {
    final Map<Long, Booking> result = new HashMap<>();
    final List<Booking> allBookings = bookingRepository.findAll();
    final List<Booking> filteredBookings = new ArrayList<>();

    for (final Booking booking: allBookings) {
      if (booking.getCandidateSlot().getDate().equals(localDate)) {
        filteredBookings.add(booking);
      }
    }

    for (final Booking booking: filteredBookings) {
      result.put(booking.getId(), booking);
    }

    return result;
  }
}
