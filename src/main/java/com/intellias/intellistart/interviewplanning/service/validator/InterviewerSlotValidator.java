package com.intellias.intellistart.interviewplanning.service.validator;

import com.intellias.intellistart.interviewplanning.exception.InvalidDayForSlotCreationException;
import com.intellias.intellistart.interviewplanning.exception.InvalidSlotDateException;
import com.intellias.intellistart.interviewplanning.model.InterviewerSlot;
import com.intellias.intellistart.interviewplanning.util.DateUtils;
import java.time.LocalDate;
import java.util.List;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * InterviewerSlot date and time period validator.
 */

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class InterviewerSlotValidator {

  /**
   * Validates date is on the next week, time period of InterviewerSlot and checks for overlapping
   * with other slots.
   */
  public static void validateSlotForNextWeek(InterviewerSlot interviewerSlot,
      List<InterviewerSlot> interviewerSlots, Long id) {
    SlotValidator.validateInterviewerSlot(interviewerSlot, interviewerSlots, id);
    boolean slotDateIsOnTheNextWeek = interviewerSlot.getWeek() == DateUtils.getNextWeek();
    boolean slotWasCreatedAfterFriday = LocalDate.now().getDayOfWeek().getValue() > 5;
    if (!slotDateIsOnTheNextWeek) {
      throw new InvalidSlotDateException(
          "Interviewer can create slots only for next week");
    } else if (slotWasCreatedAfterFriday) {
      throw new InvalidDayForSlotCreationException(
          "Interviewer slot can be created only until end of Friday of current week");
    }
  }

  /**
   * Validates date is on the current or next week, time period of InterviewerSlot and checks for
   * overlapping with other slots.
   */
  public static void validateSlotForCurrentAndNextWeek(InterviewerSlot interviewerSlot,
      List<InterviewerSlot> interviewerSlots, Long id) {
    SlotValidator.validateInterviewerSlot(interviewerSlot, interviewerSlots, id);
    boolean slotDateIsOnTheCurrentWeek = interviewerSlot.getWeek() == DateUtils.getCurrentWeek();
    boolean slotDateIsOnTheNextWeek = interviewerSlot.getWeek() == DateUtils.getNextWeek();
    if (!(slotDateIsOnTheNextWeek || slotDateIsOnTheCurrentWeek)) {
      throw new InvalidSlotDateException(
          "Coordinator can edit slots only for current or next week");
    }
  }
}
