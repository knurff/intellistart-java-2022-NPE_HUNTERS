package com.intellias.intellistart.interviewplanning.service;

import com.intellias.intellistart.interviewplanning.exception.SlotContainsBookingsException;
import com.intellias.intellistart.interviewplanning.exception.SlotNotFoundException;
import com.intellias.intellistart.interviewplanning.model.CandidateSlot;
import com.intellias.intellistart.interviewplanning.repository.CandidateSlotRepository;
import com.intellias.intellistart.interviewplanning.service.validator.CandidateSlotValidator;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * CandidateService service.
 */
@Service
public class CandidateService {

  private final CandidateSlotRepository candidateSlotRepository;

  @Autowired
  public CandidateService(CandidateSlotRepository candidateSlotRepository) {
    this.candidateSlotRepository = candidateSlotRepository;
  }

  /**
   * Returns all CandidateSlot's by email.
   */

  public List<CandidateSlot> getAllSlots(String email) {
    return candidateSlotRepository.findAllByEmail(email);
  }

  /**
   * Validates and creates CandidateSlot.
   */

  public CandidateSlot createSlot(CandidateSlot candidateSlot) {
    validateAndSaveCandidateSlot(candidateSlot);
    return candidateSlot;
  }

  /**
   * Validates and updates existing CandidateSlot.
   */

  @Transactional
  public boolean editSlot(CandidateSlot candidateSlot, Long id) {
    checkThatCandidateSlotExistsWithNoBookings(id);
    candidateSlot.setId(id);
    validateAndSaveCandidateSlot(candidateSlot);
    return true;
  }

  private void checkThatCandidateSlotExistsWithNoBookings(Long id) {
    Optional<CandidateSlot> candidateSlot = candidateSlotRepository.findById(id);
    if (candidateSlot.isEmpty()) {
      throw new SlotNotFoundException(
          String.format("Candidate slot with id: %d does not exist", id));
    } else if (candidateSlotContainsBookings(candidateSlot)) {
      throw new SlotContainsBookingsException("This slot contains bookings, you cannot change it");
    }
  }

  private void validateAndSaveCandidateSlot(CandidateSlot candidateSlot) {
    List<CandidateSlot> anotherCandidateSlots = candidateSlotRepository.findAllByEmail(
        candidateSlot.getEmail());
    CandidateSlotValidator.validate(candidateSlot, anotherCandidateSlots, candidateSlot.getId());
    candidateSlotRepository.save(candidateSlot);
  }

  private boolean candidateSlotContainsBookings(Optional<CandidateSlot> candidateSlot) {
    return !candidateSlot.get().getBookings().isEmpty();
  }

}