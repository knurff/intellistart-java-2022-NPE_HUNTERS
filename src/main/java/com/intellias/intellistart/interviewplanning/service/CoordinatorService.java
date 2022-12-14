package com.intellias.intellistart.interviewplanning.service;

import com.intellias.intellistart.interviewplanning.controller.dto.DashboardDayDto;
import com.intellias.intellistart.interviewplanning.controller.dto.DashboardDto;
import com.intellias.intellistart.interviewplanning.exception.InterviewerNotFoundException;
import com.intellias.intellistart.interviewplanning.exception.NoRoleException;
import com.intellias.intellistart.interviewplanning.exception.SelfRevokingException;
import com.intellias.intellistart.interviewplanning.exception.SlotContainsBookingsException;
import com.intellias.intellistart.interviewplanning.exception.UserAlreadyHasRoleException;
import com.intellias.intellistart.interviewplanning.model.InterviewerSlot;
import com.intellias.intellistart.interviewplanning.model.User;
import com.intellias.intellistart.interviewplanning.model.WeekBooking;
import com.intellias.intellistart.interviewplanning.model.role.UserRole;
import com.intellias.intellistart.interviewplanning.repository.InterviewerSlotRepository;
import com.intellias.intellistart.interviewplanning.repository.UserRepository;
import com.intellias.intellistart.interviewplanning.service.validator.InterviewerSlotValidator;
import com.intellias.intellistart.interviewplanning.util.DateUtils;
import com.intellias.intellistart.interviewplanning.util.RequestParser;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * CoordinatorService service.
 */
@Service
@AllArgsConstructor
public class CoordinatorService {
  private final UserRepository userRepository;
  private final InterviewerSlotRepository interviewerSlotRepository;
  private final InterviewerService interviewerService;
  private final CandidateService candidateService;
  private final BookingService bookingService;

  /**
   * Returns editSlot for {@code interviewerSlotId} relative to {@code newSlot}.
   *
   * @param interviewerId     slot owner interviewerId
   * @param interviewerSlotId id of interviewer slot
   * @param newSlot           new interviewerSlot
   * @return interviewerSlot as updated slot
   * @throws SlotContainsBookingsException if {@code oldSlot} has bookings
   */
  public InterviewerSlot editSlot(Long interviewerId, Long interviewerSlotId,
      InterviewerSlot newSlot) {

    User user = findById(interviewerId);
    checkIfSlotHasBooking(interviewerSlotId, interviewerId);

    newSlot.setId(interviewerSlotId);
    newSlot.setInterviewer(user);

    validateInterviewerSlot(newSlot, interviewerSlotId);

    return interviewerSlotRepository.save(newSlot);
  }


  private void checkIfSlotHasBooking(Long interviewerSlotId, Long interviewerId) {
    InterviewerSlot oldSlot = interviewerService.findSlotByIdAndInterviewerId(interviewerSlotId,
        interviewerId);

    if (!oldSlot.getBookings().isEmpty()) {
      throw new SlotContainsBookingsException(
          "InterviewerSlot id = " + interviewerSlotId + " has bookings");
    }
  }

  /**
   * Performs granting {@code role} for user with {@code email}.
   *
   * @param email email of user
   * @param role role which is granted
   * @return true if operation is successful
   * @throws UserAlreadyHasRoleException if user already has a role
   */
  public User grantRoleForUser(String email, UserRole role) {
    validateRoleAbsence(email);

    return saveUserWithRole(email, role);
  }

  private void validateRoleAbsence(String email) {
    Optional<User> user = userRepository.getUserByEmail(email);

    if (user.isPresent()) {
      throw new UserAlreadyHasRoleException(
          String.format("User with email: %s already has a role", email)
      );
    }
  }

  private User saveUserWithRole(String email, UserRole role) {
    User toSave = new User(role);
    final int defaultMaxBookingsPerWeek = 5;
    WeekBooking weekBooking = new WeekBooking(defaultMaxBookingsPerWeek,
            defaultMaxBookingsPerWeek);

    toSave.setEmail(email);
    toSave.setMaxBookingsPerWeek(weekBooking);
    return userRepository.save(toSave);
  }

  /**
   * Performs revoking {@code role} from user with {@code id}.
   *
   * @param id user id
   * @param role role to revoke
   * @return true if operation is successful
   * @throws NoRoleException if user doesn't have {@code role}
   * @throws SelfRevokingException if user tries to execute self-revoking
   */
  public boolean revokeRoleFromUser(Long id, UserRole role) {
    validateRoleRevoking(id, role);

    userRepository.deleteById(id);
    return true;
  }

  private void validateRoleRevoking(Long id, UserRole role) {
    Optional<User> user = userRepository.getUserByIdAndRole(id, role);

    if (user.isEmpty()) {
      throw new NoRoleException(
          String.format("User with id: %d doesn't have %s role", id, role.toString().toLowerCase())
      );
    }

    if (Objects.equals(role, UserRole.COORDINATOR)) {
      String currentUserEmail = RequestParser.getUserEmailFromToken();
      String userWithIdEmail = user.get().getEmail();

      if (Objects.equals(currentUserEmail, userWithIdEmail)) {
        throw new SelfRevokingException(
            "You can't revoke coordinator role from yourself"
        );
      }
    }
  }

  public List<User> getAllInterviewers() {
    return userRepository.getAllByRole(UserRole.INTERVIEWER);
  }

  public List<User> getAllCoordinators() {
    return userRepository.getAllByRole(UserRole.COORDINATOR);
  }

  public User findById(Long id) {
    return userRepository.findById(id).orElseThrow(
        () -> new InterviewerNotFoundException("Interviewer id = " + id + "not found"));
  }

  /**
   * Returns a dashboard dto for a week specified.
   *
   * @param weekNumber specifies the week for which to retrieve a dashboard dto.
   * @return a dashboard dto for a week specified.
   */
  public DashboardDto getDashboardForWeek(final int weekNumber) {
    return new DashboardDto(
        getDashboardForDay(weekNumber, DayOfWeek.MONDAY),
        getDashboardForDay(weekNumber, DayOfWeek.TUESDAY),
        getDashboardForDay(weekNumber, DayOfWeek.WEDNESDAY),
        getDashboardForDay(weekNumber, DayOfWeek.THURSDAY),
        getDashboardForDay(weekNumber, DayOfWeek.FRIDAY)
    );
  }

  private DashboardDayDto getDashboardForDay(final int weekNumber, final DayOfWeek dayOfWeek) {
    final LocalDate dateFromWeekAndDay = DateUtils.getDateOfDayOfWeek(weekNumber, dayOfWeek);

    return new DashboardDayDto(
        interviewerService.getAllSlotsWithRelatedBookingIdsUsingWeekAndDay(weekNumber, dayOfWeek),
        candidateService.getAllSlotsWithRelatedBookingIdsUsingDate(dateFromWeekAndDay),
        bookingService.getMapOfAllBookingsUsingDate(dateFromWeekAndDay)
    );
  }

  private void validateInterviewerSlot(InterviewerSlot interviewerSlot, Long interviewerSlotId) {
    List<InterviewerSlot> interviewerSlots = interviewerSlotRepository.getAllByInterviewer(
        interviewerSlot.getInterviewer());
    InterviewerSlotValidator.validateSlotForCurrentAndNextWeek(interviewerSlot,
        interviewerSlots,
        interviewerSlotId);
  }
}
