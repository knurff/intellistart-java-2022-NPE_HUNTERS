package com.intellias.intellistart.interviewplanning.repository;

import com.intellias.intellistart.interviewplanning.model.InterviewerSlot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * InterviewerSlotRepository repository.
 */

@Repository
public interface InterviewerSlotRepository extends JpaRepository<InterviewerSlot, Long> {

}
