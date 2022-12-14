package com.intellias.intellistart.interviewplanning.controller.dto;

import com.intellias.intellistart.interviewplanning.model.Booking;
import com.intellias.intellistart.interviewplanning.model.CandidateSlot;
import com.intellias.intellistart.interviewplanning.model.InterviewerSlot;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.Getter;

/**
 * A dto representing all bookings, coordinator and candidate slots for a particular day.
 */
@Getter
public class DashboardDayDto {

  private final Set<InterviewerSlot> interviewerSlots;
  private final Map<Long, Set<Long>> bookingIdsByInterviewerSlotId;
  private final Set<CandidateSlot> candidateSlots;
  private final Map<Long, Set<Long>> bookingIdsByCandidateSlotId;
  private final Map<Long, Booking> bookingsByIds;

  /**
   * Constructs dashboard by following maps.
   *
   * @param bookingIdsByInterviewerSlot map with booking ids of specific interviewer slot
   * @param bookingIdsByCandidateSlot map with booking ids of specific candidate slot
   * @param bookingsByIds map with bookings of specific id
   */
  public DashboardDayDto(final Map<InterviewerSlot, Set<Long>> bookingIdsByInterviewerSlot,
      final Map<CandidateSlot, Set<Long>> bookingIdsByCandidateSlot,
      final Map<Long, Booking> bookingsByIds) {

    this.bookingsByIds = bookingsByIds;

    interviewerSlots = bookingIdsByInterviewerSlot.keySet();
    candidateSlots = bookingIdsByCandidateSlot.keySet();

    bookingIdsByInterviewerSlotId = interviewerSlots.stream()
        .collect(Collectors.toMap(InterviewerSlot::getId,
            bookingIdsByInterviewerSlot::get));

    bookingIdsByCandidateSlotId = candidateSlots.stream()
        .collect(Collectors.toMap(CandidateSlot::getId,
            bookingIdsByCandidateSlot::get));
  }
}
