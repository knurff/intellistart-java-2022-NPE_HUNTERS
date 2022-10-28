package com.intellias.intellistart.interviewplanning.repository;

import com.intellias.intellistart.interviewplanning.model.Booking;
import com.intellias.intellistart.interviewplanning.model.CandidateSlot;
import com.intellias.intellistart.interviewplanning.model.InterviewerSlot;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * BookingRepository repository.
 */
@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {

  List<Booking> getAllByInterviewerSlot(final InterviewerSlot interviewerSlot);

  List<Booking> getAllByCandidateSlot(final CandidateSlot candidateSlot);

  @Query("select count(b) from Booking b "
      + "JOIN InterviewerSlot slot ON b.interviewerSlot.id = slot.id \n"
      + "JOIN User u ON slot.interviewer.id = u.id\n "
      + "WHERE slot.week = :weekNumber AND u.id = :interviewerId")
  int countAllInterviewerBookingsOnThisWeek(int weekNumber,
      Long interviewerId);
}
