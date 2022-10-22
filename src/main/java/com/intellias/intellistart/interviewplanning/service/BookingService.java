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
import java.util.function.Function;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * BookingService service.
 */
@Service
@AllArgsConstructor
public class BookingService {
  private final BookingRepository bookingRepository;

  /**
   * Returns a set of booking ids which a related to a provided interviewer slot.
   *
   * @param interviewerSlot is used to retrieve all bookings which are related to it.
   * @return a set of booking ids.
   */
  public Set<Long> getAllBookingIdsRelatedToInterviewerSlot(final InterviewerSlot interviewerSlot) {
    return bookingRepository.getAllByInterviewerSlot(interviewerSlot)
        .stream()
        .map(Booking::getId)
        .collect(Collectors.toSet());
  }

  /**
   * Returns a set of booking ids which a related to a provided candidate slot.
   *
   * @param candidateSlot is used to retrieve all bookings which are related to it.
   * @return a set of booking ids.
   */
  public Set<Long> getAllBookingIdsRelatedToCandidateSlot(final CandidateSlot candidateSlot) {
    return bookingRepository.getAllByCandidateSlot(candidateSlot)
        .stream()
        .map(Booking::getId)
        .collect(Collectors.toSet());
  }

  /**
   * Returns a map of bookings for a particular date where booking ids are used for keys.
   *
   * @param localDate specifies
   * @return a map of bookings where booking ids are used for keys.
   */
  public Map<Long, Booking> getMapOfAllBookingsUsingDate(final LocalDate localDate) {
    return bookingRepository.findAll()
        .stream()
        .filter(booking -> booking.getCandidateSlot().getDate().equals(localDate))
        .collect(Collectors.toMap(Booking::getId, Function.identity()));
  }
}
