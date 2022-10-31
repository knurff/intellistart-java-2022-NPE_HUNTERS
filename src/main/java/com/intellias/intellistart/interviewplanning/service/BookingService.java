package com.intellias.intellistart.interviewplanning.service;

import com.intellias.intellistart.interviewplanning.exception.BookingLimitExceededException;
import com.intellias.intellistart.interviewplanning.exception.BookingNotFoundException;
import com.intellias.intellistart.interviewplanning.exception.SlotNotFoundException;
import com.intellias.intellistart.interviewplanning.model.Booking;
import com.intellias.intellistart.interviewplanning.model.CandidateSlot;
import com.intellias.intellistart.interviewplanning.model.InterviewerSlot;
import com.intellias.intellistart.interviewplanning.model.User;
import com.intellias.intellistart.interviewplanning.repository.BookingRepository;
import com.intellias.intellistart.interviewplanning.repository.CandidateSlotRepository;
import com.intellias.intellistart.interviewplanning.repository.InterviewerSlotRepository;
import com.intellias.intellistart.interviewplanning.repository.UserRepository;
import com.intellias.intellistart.interviewplanning.service.validator.BookingValidator;
import java.time.LocalDate;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * BookingService service.
 */
@Service
@AllArgsConstructor
public class BookingService {

  private final BookingRepository bookingRepository;
  private final UserRepository userRepository;
  private final CandidateSlotRepository candidateSlotRepository;
  private final InterviewerSlotRepository interviewerSlotRepository;

  /**
   * Returns a set of booking ids which a related to a provided interviewer slot.
   *
   * @param interviewerSlot is used to retrieve all bookings which are related to it.
   * @return a set of booking ids.
   */
  public Set<Long> getAllBookingIdsRelatedToInterviewerSlot(final InterviewerSlot interviewerSlot) {
    return bookingRepository.getAllByInterviewerSlot(interviewerSlot)
        .stream()
        .map(Booking::getId)
        .collect(Collectors.toSet());
  }

  /**
   * Returns a set of booking ids which a related to a provided candidate slot.
   *
   * @param candidateSlot is used to retrieve all bookings which are related to it.
   * @return a set of booking ids.
   */
  public Set<Long> getAllBookingIdsRelatedToCandidateSlot(final CandidateSlot candidateSlot) {
    return bookingRepository.getAllByCandidateSlot(candidateSlot)
        .stream()
        .map(Booking::getId)
        .collect(Collectors.toSet());
  }

  /**
   * Returns a map of bookings for a particular date where booking ids are used for keys.
   *
   * @param localDate specifies
   * @return a map of bookings where booking ids are used for keys.
   */
  public Map<Long, Booking> getMapOfAllBookingsUsingDate(final LocalDate localDate) {
    return bookingRepository.findAll()
        .stream()
        .filter(booking -> booking.getCandidateSlot().getDate().equals(localDate))
        .collect(Collectors.toMap(Booking::getId, Function.identity()));
  }

  /**
   * Inserts slots into booking, validates it and creates new booking.
   *
   * @param booking           booking to create
   * @param interviewerSlotId id of interviewer slot
   * @param candidateSlotId   id of candidate slot
   * @throws SlotNotFoundException         if slots not found
   * @throws BookingLimitExceededException if interviewer booking limit for week is exceeded
   */
  public Booking createBooking(Booking booking, Long interviewerSlotId, Long candidateSlotId) {
    setInterviewerSlotForBooking(booking, interviewerSlotId);
    setCandidateSlotForBooking(booking, candidateSlotId);
    Long interviewerId = booking.getInterviewerSlot().getInterviewer().getId();
    checkThatMaxBookingIsNotExceeded(booking, interviewerId);
    BookingValidator.validate(booking);
    return bookingRepository.save(booking);
  }

  /**
   * Updates the existing booking specified with new values provided in booking dto.
   *
   * @param updatedBooking updated version of existing booking
   * @param bookingId id of the booking to update
   * @param interviewerSlotId id of interviewerSlot record associated with booking specified by
   *                          bookingId
   * @param candidateSlotId id of candidateSlot record associated with booking specified by
   *                        bookingId
   * @return updated version of booking
   */
  public Booking updateBooking(Booking updatedBooking, Long bookingId, Long interviewerSlotId,
      Long candidateSlotId) {
    updatedBooking.setId(bookingId);
    setInterviewerSlotForBooking(updatedBooking, interviewerSlotId);
    setCandidateSlotForBooking(updatedBooking, candidateSlotId);
    BookingValidator.validate(updatedBooking);
    return bookingRepository.save(updatedBooking);
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

  private void setCandidateSlotForBooking(Booking booking, Long candidateSlotId) {
    Optional<CandidateSlot> candidateSlot = candidateSlotRepository.findById(
        candidateSlotId);
    if (candidateSlot.isEmpty()) {
      throw new SlotNotFoundException(
          String.format("Cannot find candidate slot with id: %d", candidateSlotId));
    } else {
      booking.setCandidateSlot(candidateSlot.get());
    }
  }

  private void setInterviewerSlotForBooking(Booking booking, Long interviewerSlotId) {
    Optional<InterviewerSlot> interviewerSlot = interviewerSlotRepository.findById(
        interviewerSlotId);
    if (interviewerSlot.isEmpty()) {
      throw new SlotNotFoundException(
          String.format("Cannot find interviewer slot with id: %d", interviewerSlotId));
    } else {
      booking.setInterviewerSlot(interviewerSlot.get());
    }
  }

  private void checkThatMaxBookingIsNotExceeded(Booking booking, Long interviewerId) {
    User user = userRepository.findById(interviewerId).get();

    long numberOfAllBookingsForThisWeek = bookingRepository.countAllInterviewerBookingsOnThisWeek(
        booking.getInterviewerSlot().getWeek(), interviewerId);

    if (numberOfAllBookingsForThisWeek + 1 > user.getMaxBookingsPerWeek().getCurrentWeek()) {
      throw new BookingLimitExceededException(
          String.format("Booking limit for interviewer with id: %d exceeded", interviewerId));
    }
  }

  private void checkThatBookingExists(Long bookingId) {
    if (bookingRepository.findById(bookingId).isEmpty()) {
      throw new BookingNotFoundException(
          String.format("Booking with id: %d not found", bookingId));
    }
  }
}