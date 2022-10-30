package com.intellias.intellistart.interviewplanning.service;

import com.intellias.intellistart.interviewplanning.exception.InterviewerNotFoundException;
import com.intellias.intellistart.interviewplanning.exception.SlotIsOverlappingException;
import com.intellias.intellistart.interviewplanning.exception.SlotNotFoundException;
import com.intellias.intellistart.interviewplanning.model.InterviewerSlot;
import com.intellias.intellistart.interviewplanning.model.User;
import com.intellias.intellistart.interviewplanning.model.role.UserRole;
import com.intellias.intellistart.interviewplanning.repository.InterviewerSlotRepository;
import com.intellias.intellistart.interviewplanning.repository.UserRepository;
import com.intellias.intellistart.interviewplanning.service.validator.TimePeriodValidator;
import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * InterviewerService service.
 */
@Service
@AllArgsConstructor
public class InterviewerService {

  private final InterviewerSlotRepository interviewerSlotRepository;
  private final UserRepository userRepository;
  private final BookingService bookingService;

  public InterviewerSlot createSlot() {
    return new InterviewerSlot();
  }

  public boolean editSlot() {
    return true;
  }

  public List<InterviewerSlot> getAllSlots() {
    return new ArrayList<>();
  }

  /**
   * Set max quantity of bookings to next week.
   *
   * @param interviewerId long id of interviewer
   * @param maxBookings quantity of booking to next week
   * @throws InterviewerNotFoundException if {@code interviewerId} is not id of interviewer.
   */
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

    return interviewerSlotRepository.getAllByInterviewerId(interviewer);
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
        slot.getInterviewerId().getId().equals(interviewerId);

    if (!slotBelongsToInterviewerSpecified) {
      throw new SlotNotFoundException(
          " Slot id = " + interviewerSlotId + " with Interviewer id = " + interviewerId
              + " not found");
    }

    return slot;
  }

  /**
   * Returns list of interviewerSlot relate {@code user}.
   */
  public List<InterviewerSlot> findAllByInterviewerId(User user) {
    return interviewerSlotRepository.getAllByInterviewerId(user);
  }

  public InterviewerSlot save(InterviewerSlot slot) {
    return interviewerSlotRepository.save(slot);
  }

  /**
   * Check overlapping relate on {@code slot}.
   *
   * @throws SlotIsOverlappingException if {@code slot} is overlapping
   */
  public void checkSlotOverlapping(InterviewerSlot slot) {
    List<InterviewerSlot> interviewerSlots = findAllByInterviewerId(slot.getInterviewerId());

    if (isSlotOverlapping(interviewerSlots, slot)) {
      throw new SlotIsOverlappingException("Slot already exist");
    }
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

  private boolean isSlotOverlapping(List<InterviewerSlot> interviewerSlots, InterviewerSlot slot) {
    return interviewerSlots.stream()
        .filter(anotherSlot -> checkThatDatesAreEqualAndIdAreNot(slot, anotherSlot))
        .anyMatch(anotherSlot -> TimePeriodValidator.isOverlapping(slot.getPeriod(),
            anotherSlot.getPeriod()));
  }

  private boolean checkThatDatesAreEqualAndIdAreNot(InterviewerSlot interviewerSlot,
      InterviewerSlot anotherInterviewerSlot) {
    boolean datesAreEqual = interviewerSlot.getDate().equals(anotherInterviewerSlot.getDate());
    return datesAreEqual && checkThatIdIsNullOrNotEqualWithAnotherSlotId(interviewerSlot.getId(),
        anotherInterviewerSlot);
  }

  private boolean checkThatIdIsNullOrNotEqualWithAnotherSlotId(Long id,
      InterviewerSlot anotherInterviewerSlot) {
    return id == null || !id.equals(anotherInterviewerSlot.getId());
  }
}
