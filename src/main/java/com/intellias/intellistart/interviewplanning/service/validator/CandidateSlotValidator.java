package com.intellias.intellistart.interviewplanning.service.validator;

import com.intellias.intellistart.interviewplanning.exception.InvalidCandidateSlotDateException;
import com.intellias.intellistart.interviewplanning.exception.InvalidTimeSlotBoundariesException;
import com.intellias.intellistart.interviewplanning.exception.SlotIsOverlappingException;
import com.intellias.intellistart.interviewplanning.model.CandidateSlot;
import com.intellias.intellistart.interviewplanning.model.TimePeriod;
import com.intellias.intellistart.interviewplanning.util.TimeConverter;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * CandidateSlot date and time period validator.
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CandidateSlotValidator {

  /**
   * Method, that validates date and time period of CandidateSlot and checks for overlapping with
   * other slots.
   */

  public static void validate(CandidateSlot candidateSlot,
      List<CandidateSlot> anotherCandidateSlots, Long id) {
    checkThatSlotTimeAndDateAreInFuture(candidateSlot);
    checkThatSlotTimeDurationIsValidAndRounded(candidateSlot.getPeriod());
    checkThatSlotIsNotOverlappingWithAnotherSlot(candidateSlot, anotherCandidateSlots, id);
  }

  private static void checkThatSlotIsNotOverlappingWithAnotherSlot(CandidateSlot candidateSlot,
      List<CandidateSlot> anotherCandidateSlots, Long id) {
    if (overlappingExists(candidateSlot, anotherCandidateSlots, id)) {
      throw new SlotIsOverlappingException("This slot is overlapping another");
    }
  }

  private static void checkThatSlotTimeAndDateAreInFuture(CandidateSlot candidateSlot) {
    TimePeriod timePeriod = candidateSlot.getPeriod();
    if (slotDateIsInThePastCondition(candidateSlot)) {
      throw new InvalidCandidateSlotDateException("Candidate slot date must be in future");
    } else if (candidateSlot.getDate().isEqual(LocalDate.now()) && slotTimeIsInThePastCondition(
        timePeriod)) {
      throw new InvalidTimeSlotBoundariesException("Candidate slot time period must be in future");
    }
  }

  private static boolean slotDateIsInThePastCondition(CandidateSlot candidateSlot) {
    return candidateSlot.getDate().isBefore(LocalDate.now());
  }

  private static boolean slotTimeIsInThePastCondition(TimePeriod timePeriod) {
    return timePeriod.getStartTime().isBefore(LocalTime.now()) || timePeriod.getEndTime()
        .isBefore(LocalTime.now());
  }

  private static void checkThatSlotTimeDurationIsValidAndRounded(TimePeriod timePeriod) {
    int startTimeMinutes = TimeConverter.convertTimeToMinutes(timePeriod.getStartTime());
    int endTimeMinutes = TimeConverter.convertTimeToMinutes(timePeriod.getEndTime());
    if (endTimeMinutes - startTimeMinutes < 90) {
      throw new InvalidTimeSlotBoundariesException("Slot has to be 1,5 hours or more");
    } else if (startTimeMinutes % 30 != 0 || endTimeMinutes % 30 != 0) {
      throw new InvalidTimeSlotBoundariesException("Slot has to be rounded to 30 minutes");
    }
  }

  private static boolean isOverlapping(TimePeriod period1, TimePeriod period2) {
    return period1.getStartTime().compareTo(period2.getEndTime()) <= 0
        && period1.getEndTime().compareTo(period2.getStartTime()) >= 0;
  }

  private static boolean overlappingExists(CandidateSlot candidateSlot,
      List<CandidateSlot> anotherCandidateSlots, Long id) {
    return anotherCandidateSlots.stream()
        .filter(anotherSlot -> checkThatDatesAreEqualAndIdAreNot(candidateSlot, anotherSlot, id))
        .anyMatch(anotherSlot -> isOverlapping(candidateSlot.getPeriod(), anotherSlot.getPeriod()));
  }

  private static boolean checkThatDatesAreEqualAndIdAreNot(CandidateSlot candidateSlot,
      CandidateSlot anotherCandidateSlot, Long id) {
    return candidateSlot.getDate().equals(anotherCandidateSlot.getDate())
        && checkThatIdIsNullOrNotEqualWithAnotherSlotId(id, anotherCandidateSlot);
  }

  private static boolean checkThatIdIsNullOrNotEqualWithAnotherSlotId(Long id,
      CandidateSlot anotherCandidateSlot) {
    if (id == null) {
      return true;
    } else {
      return !id.equals(anotherCandidateSlot.getId());
    }
  }
}
