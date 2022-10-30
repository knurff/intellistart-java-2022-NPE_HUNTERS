package com.intellias.intellistart.interviewplanning.service;

import com.intellias.intellistart.interviewplanning.controller.dto.DashboardDayDto;
import com.intellias.intellistart.interviewplanning.controller.dto.DashboardDto;
import com.intellias.intellistart.interviewplanning.exception.BookingNotFoundException;
import com.intellias.intellistart.interviewplanning.exception.InterviewerNotFoundException;
import com.intellias.intellistart.interviewplanning.exception.SlotContainsBookingsException;
import com.intellias.intellistart.interviewplanning.exception.SlotNotFoundException;
import com.intellias.intellistart.interviewplanning.model.Booking;
import com.intellias.intellistart.interviewplanning.model.CandidateSlot;
import com.intellias.intellistart.interviewplanning.model.InterviewerSlot;
import com.intellias.intellistart.interviewplanning.model.User;
import com.intellias.intellistart.interviewplanning.model.role.UserRole;
import com.intellias.intellistart.interviewplanning.repository.BookingRepository;
import com.intellias.intellistart.interviewplanning.repository.CandidateSlotRepository;
import com.intellias.intellistart.interviewplanning.repository.InterviewerSlotRepository;
import com.intellias.intellistart.interviewplanning.repository.UserRepository;
import com.intellias.intellistart.interviewplanning.service.validator.InterviewerSlotValidator;
import com.intellias.intellistart.interviewplanning.service.validator.SlotValidator;
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
  private final InterviewerSlotRepository interviewerSlotRepository;
  private final CandidateSlotRepository candidateSlotRepository;
  private final BookingRepository bookingRepository;
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

  public Booking updateBooking(Long bookingId, Long candidateSlotId,
      Long interviewerSlotId, Booking updatedBooking) {

    Booking oldBooking = bookingRepository.findById(bookingId).orElseThrow(
        () -> new BookingNotFoundException("Booking specified by id = " + bookingId + " does not exist"));

    if (oldBooking.equals(updatedBooking)) {
      return updatedBooking;
    }

    CandidateSlot associatedCandidateSlot =
        candidateSlotRepository.findById(candidateSlotId)
            .orElseThrow(() -> new SlotNotFoundException("Candidate slot with id = " +
                candidateSlotId + " was not found"));

    InterviewerSlot associatedInterviewerSlot =
        interviewerSlotRepository.findById(interviewerSlotId)
            .orElseThrow(() -> new SlotNotFoundException("Interviewer slot with id = " +
                interviewerSlotId + " was not found"));

    validateCandidateSlot(associatedCandidateSlot, candidateSlotId);
    validateInterviewerSlot(associatedInterviewerSlot, interviewerSlotId);

    updatedBooking.setCandidateSlot(associatedCandidateSlot);
    updatedBooking.setInterviewerSlot(associatedInterviewerSlot);

    return bookingRepository.saveAndFlush(updatedBooking);
  }

  private DashboardDayDto getDashboardForDay(final int weekNumber, final DayOfWeek dayOfWeek) {
    final LocalDate dateFromWeekAndDay = DateUtils.getDateOfDayOfWeek(weekNumber, dayOfWeek);

    return new DashboardDayDto(
        interviewerService.getAllSlotsWithRelatedBookingIdsUsingWeekAndDay(weekNumber, dayOfWeek),
        candidateService.getAllSlotsWithRelatedBookingIdsUsingDate(dateFromWeekAndDay),
        bookingService.getMapOfAllBookingsUsingDate(dateFromWeekAndDay)
    );
  }

  private void validateCandidateSlot(CandidateSlot candidateSlot, Long candidateSlotId) {
    List<CandidateSlot> candidateSlots = candidateSlotRepository.findAllByEmail(
        candidateSlot.getEmail());
    SlotValidator.validateCandidateSlot(candidateSlot, candidateSlots,
        candidateSlotId);
  }

  private void validateInterviewerSlot(InterviewerSlot interviewerSlot, Long interviewerSlotId) {
    List<InterviewerSlot> interviewerSlots = interviewerSlotRepository.getAllByInterviewer(
        interviewerSlot.getInterviewer());
    InterviewerSlotValidator.validateSlotForCurrentAndNextWeek(interviewerSlot,
        interviewerSlots,
        interviewerSlotId);
  }
}
