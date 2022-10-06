package com.intellias.intellistart.interviewplanning.repository;

import com.intellias.intellistart.interviewplanning.model.Slot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * SlotRepository repository.
 */
@Repository
public interface SlotRepository extends JpaRepository<Slot, Long> {

}
