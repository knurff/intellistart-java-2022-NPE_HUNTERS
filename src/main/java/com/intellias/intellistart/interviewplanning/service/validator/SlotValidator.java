package com.intellias.intellistart.interviewplanning.service.validator;

import com.intellias.intellistart.interviewplanning.exception.InvalidTimePeriodBoundaries;
import com.intellias.intellistart.interviewplanning.exception.SlotIsOverlappingException;
import com.intellias.intellistart.interviewplanning.model.CandidateSlot;
import com.intellias.intellistart.interviewplanning.model.InterviewerSlot;
import com.intellias.intellistart.interviewplanning.model.Slot;
import com.intellias.intellistart.interviewplanning.model.TimePeriod;
import com.intellias.intellistart.interviewplanning.util.DateUtils;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * Validates generic conditions for Slot.
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SlotValidator {

  /**
   * Performs basic and some specific validation for CandidateSlot.
   */
  public static void validateCandidateSlot(CandidateSlot candidateSlot,
      List<CandidateSlot> anotherSlots, Long slotId) {
    performBasicSlotValidation(candidateSlot, anotherSlots, slotId);
    TimePeriodValidator.checkTimePeriodWithoutWorkingHoursAndDurationMoreThanMin(
        candidateSlot.getPeriod());
  }

  /**
   * Performs basic and some specific validation for InterviewerSlot.
   */
  public static void validateInterviewerSlot(InterviewerSlot interviewerSlot,
      List<InterviewerSlot> anotherSlots, Long slotId) {
    performBasicSlotValidation(interviewerSlot, anotherSlots, slotId);
    TimePeriodValidator.checkTimePeriodWithWorkingHoursAndDurationMoreThanMin(
        interviewerSlot.getPeriod());
  }

  private static void performBasicSlotValidation(Slot slot,
      List<? extends Slot> anotherSlots, Long slotId) {
    checkThatSlotTimeAndDateAreInFuture(slot);
    checkThatSlotIsNotOverlappingWithAnotherSlot(slot, (List<Slot>) anotherSlots, slotId);
  }

  private static void checkThatSlotTimeAndDateAreInFuture(Slot slot) {
    TimePeriod timePeriod = slot.getPeriod();
    boolean slotDateEqualsCurrentDate = slot.getDate().isEqual(LocalDate.now());
    DateUtils.checkDateIsInFuture(slot.getDate());
    if (slotDateEqualsCurrentDate && slotTimeIsInThePastCondition(timePeriod)) {
      throw new InvalidTimePeriodBoundaries("Slot time period must be in future");
    }
  }

  private static boolean slotTimeIsInThePastCondition(TimePeriod timePeriod) {
    return timePeriod.getStartTime().isBefore(LocalTime.now()) || timePeriod.getEndTime()
        .isBefore(LocalTime.now());
  }

  private static void checkThatSlotIsNotOverlappingWithAnotherSlot(Slot slot,
      List<Slot> anotherSlots, Long slotId) {
    if (overlappingExists(slot, anotherSlots, slotId)) {
      throw new SlotIsOverlappingException("This slot is overlapping another");
    }
  }

  private static boolean overlappingExists(Slot slot,
      List<Slot> anotherSlots, Long slotId) {
    return anotherSlots.stream()
        .filter(anotherSlot -> checkThatDatesAreEqualAndIdAreNot(slot, anotherSlot, slotId))
        .anyMatch(anotherSlot -> TimePeriodValidator.isOverlapping(slot.getPeriod(),
            anotherSlot.getPeriod()));
  }

  private static boolean checkThatDatesAreEqualAndIdAreNot(Slot slot,
      Slot anotherSlot, Long slotId) {
    boolean datesAreEqual = slot.getDate().equals(anotherSlot.getDate());
    return datesAreEqual && checkThatIdIsNullOrNotEqualWithAnotherSlotId(slotId,
        anotherSlot);
  }

  private static boolean checkThatIdIsNullOrNotEqualWithAnotherSlotId(Long slotId,
      Slot anotherSlots) {
    return slotId == null || !slotId.equals(anotherSlots.getId());
  }
}
