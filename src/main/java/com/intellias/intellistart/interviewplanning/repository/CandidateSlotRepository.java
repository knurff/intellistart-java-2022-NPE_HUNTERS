package com.intellias.intellistart.interviewplanning.repository;

import com.intellias.intellistart.interviewplanning.model.CandidateSlot;
import java.time.LocalDate;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * CandidateSlotRepository repository.
 */

@Repository
public interface CandidateSlotRepository extends JpaRepository<CandidateSlot, Long> {
  List<CandidateSlot> findAllByEmail(final String email);

  List<CandidateSlot> getAllByDate(final LocalDate localDate);
}
