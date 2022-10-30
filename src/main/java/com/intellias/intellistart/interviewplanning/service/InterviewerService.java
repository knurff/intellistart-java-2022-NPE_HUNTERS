package com.intellias.intellistart.interviewplanning.service;

import com.intellias.intellistart.interviewplanning.exception.InterviewerNotFoundException;
import com.intellias.intellistart.interviewplanning.exception.SlotNotFoundException;
import com.intellias.intellistart.interviewplanning.model.InterviewerSlot;
import com.intellias.intellistart.interviewplanning.model.User;
import com.intellias.intellistart.interviewplanning.model.role.UserRole;
import com.intellias.intellistart.interviewplanning.repository.InterviewerSlotRepository;
import com.intellias.intellistart.interviewplanning.repository.UserRepository;
import com.intellias.intellistart.interviewplanning.service.validator.InterviewerSlotValidator;
import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import javax.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * InterviewerService service.
 */
@Service
@AllArgsConstructor
public class InterviewerService {

  private final InterviewerSlotRepository interviewerSlotRepository;
  private final UserRepository userRepository;
  private final BookingService bookingService;

  /**
   * Finds interviewer, sets it into slot, validates and creates new InterviewerSlot.
   *
   * @return interviewerSlot created entity
   * @throws InterviewerNotFoundException if {@code interviewerId} is not id of interviewer.
   */
  public InterviewerSlot createSlot(InterviewerSlot interviewerSlot, Long interviewerId) {
    findInterviewerAndSetIntoSlot(interviewerSlot, interviewerId);
    return validateSlotAndSave(interviewerSlot);
  }

  /**
   * Finds interviewer and slot to update, sets interviewer and slotId into slot, validates and
   * updates existent InterviewerSlot.
   *
   * @return interviewerSlot updated entity
   * @throws InterviewerNotFoundException if {@code interviewerId} is not id of interviewer.
   * @throws SlotNotFoundException        if {@code slotId} is not id of existent interviewer slot.
   */
  public InterviewerSlot editSlot(InterviewerSlot interviewerSlot, Long interviewerId,
      Long slotId) {
    findSlotById(slotId);
    findInterviewerAndSetIntoSlot(interviewerSlot, interviewerId);
    interviewerSlot.setId(slotId);
    return validateSlotAndSave(interviewerSlot);
  }

  public List<InterviewerSlot> getAllSlots() {
    return new ArrayList<>();
  }

  @Transactional
  public void setMaxBookings(Long interviewerId, int maxBookings) {
    int currentWeekBookings = getInterviewerOrThrowException(interviewerId).getMaxBookingsPerWeek()
        .getCurrentWeek();
    userRepository.setMaxBookings(interviewerId, currentWeekBookings, maxBookings);
  }

  /**
   * Returns all interviewer's slots.
   *
   * @param interviewerId long id of interviewer
   * @return list of interviewers slots.
   * @throws InterviewerNotFoundException if {@code interviewerId} is not id of interviewer.
   */
  public List<InterviewerSlot> getAllInterviewerSlotsByInterviewerId(Long interviewerId) {
    var interviewer = getInterviewerOrThrowException(interviewerId);

    return interviewerSlotRepository.getAllByInterviewer(interviewer);
  }

  /**
   * Returns interviewer if user exist.
   *
   * @param interviewerId long id of interviewer
   * @return User.
   * @throws InterviewerNotFoundException if {@code interviewerId} is not id of interviewer.
   */

  public User getInterviewerOrThrowException(Long interviewerId) {
    Optional<User> interviewerOptional = userRepository.findById(interviewerId);

    final boolean interviewerNotFoundOrRoleNotInterviewer = interviewerOptional.isEmpty()
        || !interviewerOptional.get().getRole().equals(UserRole.INTERVIEWER);

    if (interviewerNotFoundOrRoleNotInterviewer) {
      throw new InterviewerNotFoundException(
          String.format("Interviewer with id %s doesn't exist", interviewerId));
    }

    return interviewerOptional.get();
  }

  public InterviewerSlot findSlotById(Long interviewerSlotId) {
    return interviewerSlotRepository.findById(interviewerSlotId).orElseThrow(
        () -> new SlotNotFoundException(" Slot id = " + interviewerSlotId + " not found"));
  }

  /**
   * Returns InterviewerSlot  relative to {@code interviewerSlotId} and {@code interviewerId}.
   *
   * @param interviewerId     slot owner interviewerId
   * @param interviewerSlotId id of interviewer slot
   * @return slot
   * @throws SlotNotFoundException if {@code slot} is not {@code interviewerId}
   */
  public InterviewerSlot findSlotByIdAndInterviewerId(Long interviewerSlotId, Long interviewerId) {
    final InterviewerSlot slot = findSlotById(interviewerSlotId);

    final boolean slotBelongsToInterviewerSpecified =
        slot.getInterviewer().getId().equals(interviewerId);

    if (!slotBelongsToInterviewerSpecified) {
      throw new SlotNotFoundException(
          " Slot id = " + interviewerSlotId + " with Interviewer id = " + interviewerId
              + " not found");
    }

    return slot;
  }

  /**
   * Returns a map of interviewer slots as keys and booking id sets related to them as values for a
   * particular week and day.
   *
   * @param weekNumber a number of the week.
   * @param dayOfWeek  a day of the week specified.
   * @return a map of interviewer slots as keys and booking id sets related to them as values.
   */
  public Map<InterviewerSlot, Set<Long>> getAllSlotsWithRelatedBookingIdsUsingWeekAndDay(
      final int weekNumber,
      final DayOfWeek dayOfWeek) {

    final Map<InterviewerSlot, Set<Long>> result = new HashMap<>();

    final List<InterviewerSlot> allSlots =
        interviewerSlotRepository.getAllByWeekAndDayOfWeek(weekNumber, dayOfWeek);

    for (final InterviewerSlot slot : allSlots) {
      final Set<Long> relatedBookingIds =
          bookingService.getAllBookingIdsRelatedToInterviewerSlot(slot);

      result.put(slot, relatedBookingIds);
    }

    return result;
  }

  private void findInterviewerAndSetIntoSlot(InterviewerSlot interviewerSlot, Long interviewerId) {
    User interviewer = getInterviewerOrThrowException(interviewerId);
    interviewerSlot.setInterviewer(interviewer);
  }

  private InterviewerSlot validateSlotAndSave(InterviewerSlot interviewerSlot) {
    List<InterviewerSlot> interviewerSlots = interviewerSlotRepository.getAllByInterviewer(
        interviewerSlot.getInterviewer());
    InterviewerSlotValidator.validateSlotForNextWeek(interviewerSlot, interviewerSlots,
        interviewerSlot.getId());
    return interviewerSlotRepository.save(interviewerSlot);
  }
}