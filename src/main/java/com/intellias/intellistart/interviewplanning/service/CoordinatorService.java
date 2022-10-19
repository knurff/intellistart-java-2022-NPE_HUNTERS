package com.intellias.intellistart.interviewplanning.service;

import com.intellias.intellistart.interviewplanning.exception.BookingNotFoundException;
import com.intellias.intellistart.interviewplanning.model.Booking;
import com.intellias.intellistart.interviewplanning.model.CandidateSlot;
import com.intellias.intellistart.interviewplanning.model.User;
import com.intellias.intellistart.interviewplanning.model.role.UserRole;
import com.intellias.intellistart.interviewplanning.repository.BookingRepository;
import com.intellias.intellistart.interviewplanning.repository.UserRepository;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;

/**
 * CoordinatorService service.
 */
@Service
public class CoordinatorService {

  private final UserRepository userRepository;
  private final BookingRepository bookingRepository;

  public CoordinatorService(UserRepository userRepository, BookingRepository bookingRepository) {
    this.userRepository = userRepository;
    this.bookingRepository = bookingRepository;
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

  /**
   * Deletes booking by id.
   *
   * @param bookingId long id of booking
   * @throws BookingNotFoundException if booking with {@code bookingId} is not found.
   */
  public boolean deleteBooking(Long bookingId) {
    checkThatBookingExists(bookingId);
    bookingRepository.deleteById(bookingId);
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

  private void checkThatBookingExists(Long bookingId) {
    if (bookingRepository.findById(bookingId).isEmpty()) {
      throw new BookingNotFoundException(
          String.format("Booking with id: %d not found", bookingId));
    }
  }
}
