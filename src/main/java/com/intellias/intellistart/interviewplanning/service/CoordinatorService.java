package com.intellias.intellistart.interviewplanning.service;

import com.intellias.intellistart.interviewplanning.model.Booking;
import com.intellias.intellistart.interviewplanning.model.CandidateSlot;
import com.intellias.intellistart.interviewplanning.model.User;
import com.intellias.intellistart.interviewplanning.model.role.UserRole;
import com.intellias.intellistart.interviewplanning.repository.UserRepository;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * CoordinatorService service.
 */
@Service
public class CoordinatorService {

  private final UserRepository userRepository;

  @Autowired
  public CoordinatorService(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  public Booking createBooking() {
    return new Booking();
  }

  public boolean editSlot() {
    return true;
  }

  public boolean editBooking() {
    return true;
  }

  public boolean grantRoleForUser() {
    return true;
  }

  public boolean removeRoleFromUser() {
    return true;
  }

  public List<CandidateSlot> getAllUsersSlots() {
    return new ArrayList<>();
  }

  public List<User> getUsersByRole() {
    return new ArrayList<>();
  }

  public List<User> getAllInterviewers() {
    return userRepository.getAllByRole(UserRole.INTERVIEWER);
  }

  public List<User> getAllCoordinators() {
    return userRepository.getAllByRole(UserRole.COORDINATOR);
  }
}
