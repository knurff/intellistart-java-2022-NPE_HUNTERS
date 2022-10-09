package com.intellias.intellistart.interviewplanning.repository;

import com.intellias.intellistart.interviewplanning.model.CandidateSlot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * CandidateSlotRepository repository.
 */

@Repository
public interface CandidateSlotRepository extends JpaRepository<CandidateSlot, Long> {

}
