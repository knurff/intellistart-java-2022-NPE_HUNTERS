package com.intellias.intellistart.interviewplanning.service;

import com.intellias.intellistart.interviewplanning.exception.SlotContainsBookingsException;
import com.intellias.intellistart.interviewplanning.exception.SlotNotFoundException;
import com.intellias.intellistart.interviewplanning.model.CandidateSlot;
import com.intellias.intellistart.interviewplanning.repository.CandidateSlotRepository;
import com.intellias.intellistart.interviewplanning.service.validator.SlotOwnerValidator;
import com.intellias.intellistart.interviewplanning.service.validator.SlotValidator;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

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
  public CandidateSlot editSlot(CandidateSlot candidateSlot, Long id) {
    CandidateSlot candidateSlotFromDb = candidateSlotRepository.findById(id).orElseThrow(() ->
        new SlotNotFoundException(
            String.format("Candidate slot with id: %d does not exist", id)));

    SlotOwnerValidator.validateSlotOwner(candidateSlotFromDb.getEmail(), candidateSlot.getEmail());

    if (!candidateSlotFromDb.getBookings().isEmpty()) {
      throw new SlotContainsBookingsException("This slot contains bookings, you cannot change it");
    }

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

  private CandidateSlot validateAndSaveCandidateSlot(CandidateSlot candidateSlot) {
    List<CandidateSlot> anotherCandidateSlots = candidateSlotRepository.findAllByEmail(
        candidateSlot.getEmail());

    SlotValidator.validateCandidateSlot(candidateSlot, anotherCandidateSlots,
        candidateSlot.getId());

    return candidateSlotRepository.save(candidateSlot);
  }
}