package com.intellias.intellistart.interviewplanning.service;

import com.intellias.intellistart.interviewplanning.controller.dto.DashboardDayDto;
import com.intellias.intellistart.interviewplanning.controller.dto.DashboardDto;
import com.intellias.intellistart.interviewplanning.exception.InterviewerNotFoundException;
import com.intellias.intellistart.interviewplanning.exception.SlotContainsBookingsException;
import com.intellias.intellistart.interviewplanning.model.Booking;
import com.intellias.intellistart.interviewplanning.model.InterviewerSlot;
import com.intellias.intellistart.interviewplanning.model.User;
import com.intellias.intellistart.interviewplanning.model.role.UserRole;
import com.intellias.intellistart.interviewplanning.repository.UserRepository;
import com.intellias.intellistart.interviewplanning.service.validator.TimePeriodValidator;
import com.intellias.intellistart.interviewplanning.util.DateUtils;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import org.aspectj.weaver.patterns.ConcreteCflowPointcut.Slot;
import org.springframework.stereotype.Service;

/**
 * CoordinatorService service.
 */
@Service
@AllArgsConstructor
public class CoordinatorService {
  private final UserRepository userRepository;
  private final InterviewerService interviewerService;
  private final CandidateService candidateService;
  private final BookingService bookingService;

  public Booking createBooking() {
    return new Booking();
  }

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
    newSlot.setInterviewerId(user);

    validateSlot(newSlot);

    return interviewerService.save(newSlot);
  }

  private void checkIfSlotHasBooking(Long interviewerSlotId, Long interviewerId) {
    InterviewerSlot oldSlot = interviewerService.findSlotByIdAndInterviewerId(interviewerSlotId,
        interviewerId);

    if (!oldSlot.getBookings().isEmpty()) {
      throw new SlotContainsBookingsException(
          "InterviewerSlot id = " + interviewerSlotId + " has bookings");
    }
  }

  private void validateSlot(InterviewerSlot slot) {
    DateUtils.checkDateIsInFuture(slot.getDate());
    TimePeriodValidator.checkTimePeriod(slot.getPeriod());
    interviewerService.checkSlotOverlapping(slot);
  }

  public boolean editBooking() {
    return true;
  }

  public boolean deleteBooking() {
    return true;
  }

  public boolean grantRoleForUser() {
    return true;
  }

  public boolean removeRoleFromUser() {
    return true;
  }

  public List<Slot> getAllUsersSlots() {
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
}
