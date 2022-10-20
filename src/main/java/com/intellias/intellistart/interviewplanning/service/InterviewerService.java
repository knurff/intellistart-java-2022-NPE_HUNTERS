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
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
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

  public InterviewerSlot createSlot() {
    return new InterviewerSlot();
  }

  public boolean editSlot() {
    return true;
  }

  public List<InterviewerSlot> getAllSlots() {
    return new ArrayList<>();
  }

  public void setMaxBookings() {

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

    if (interviewerOptional.isEmpty()
        || !interviewerOptional.get().getRole().equals(UserRole.INTERVIEWER)) {

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
    InterviewerSlot slot = findSlotById(interviewerSlotId);

    if (!slot.getInterviewerId().getId().equals(interviewerId)) {
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
