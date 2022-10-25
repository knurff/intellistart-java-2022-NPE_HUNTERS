package com.intellias.intellistart.interviewplanning.service;

import com.intellias.intellistart.interviewplanning.exception.SlotContainsBookingsException;
import com.intellias.intellistart.interviewplanning.exception.SlotNotFoundException;
import com.intellias.intellistart.interviewplanning.model.CandidateSlot;
import com.intellias.intellistart.interviewplanning.repository.CandidateSlotRepository;
import com.intellias.intellistart.interviewplanning.service.validator.CandidateSlotValidator;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * CandidateService service.
 */
@Service
@AllArgsConstructor
public class CandidateService {

  private final CandidateSlotRepository candidateSlotRepository;
  private final BookingService bookingService;

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
    return validateAndSaveCandidateSlot(candidateSlot);
  }

  /**
   * Validates and updates existing CandidateSlot.
   */

  @Transactional
  public CandidateSlot editSlot(CandidateSlot candidateSlot, Long id) {
    checkThatCandidateSlotExistsWithNoBookings(id);
    candidateSlot.setId(id);
    return validateAndSaveCandidateSlot(candidateSlot);
  }

  /**
   * Returns a map of candidate slots as keys and booking id sets related to them as values for a
   * particular date.
   *
   * @param localDate specifies the date using which to retrieve candidate slots.
   * @return a map of candidate slots as keys and booking id sets related to them as values for a
   *     particular date.
   */
  public Map<CandidateSlot, Set<Long>> getAllSlotsWithRelatedBookingIdsUsingDate(
      final LocalDate localDate) {

    final Map<CandidateSlot, Set<Long>> result = new HashMap<>();
    final List<CandidateSlot> allSlots = candidateSlotRepository.getAllByDate(localDate);

    for (final CandidateSlot slot : allSlots) {
      final Set<Long> relatedBookingIds =
          bookingService.getAllBookingIdsRelatedToCandidateSlot(slot);

      result.put(slot, relatedBookingIds);
    }

    return result;
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

  private CandidateSlot validateAndSaveCandidateSlot(CandidateSlot candidateSlot) {
    List<CandidateSlot> anotherCandidateSlots = candidateSlotRepository.findAllByEmail(
        candidateSlot.getEmail());
    CandidateSlotValidator.validate(candidateSlot, anotherCandidateSlots, candidateSlot.getId());
    return candidateSlotRepository.save(candidateSlot);
  }

  private boolean candidateSlotContainsBookings(Optional<CandidateSlot> candidateSlot) {
    return !candidateSlot.get().getBookings().isEmpty();
  }
}