package com.intellias.intellistart.interviewplanning.repository;

import com.intellias.intellistart.interviewplanning.model.Booking;
import com.intellias.intellistart.interviewplanning.model.CandidateSlot;
import com.intellias.intellistart.interviewplanning.model.InterviewerSlot;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * BookingRepository repository.
 */
@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {
  List<Booking> getAllByInterviewerSlot(final InterviewerSlot interviewerSlot);
  List<Booking> getAllByCandidateSlot(final CandidateSlot candidateSlot);
}
