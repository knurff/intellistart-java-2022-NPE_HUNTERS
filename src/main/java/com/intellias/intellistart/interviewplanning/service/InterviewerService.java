package com.intellias.intellistart.interviewplanning.service;

import com.intellias.intellistart.interviewplanning.exception.InterviewerNotFoundException;
import com.intellias.intellistart.interviewplanning.model.InterviewerSlot;
import com.intellias.intellistart.interviewplanning.model.User;
import com.intellias.intellistart.interviewplanning.model.role.UserRole;
import com.intellias.intellistart.interviewplanning.repository.InterviewerSlotRepository;
import com.intellias.intellistart.interviewplanning.repository.UserRepository;
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
}
