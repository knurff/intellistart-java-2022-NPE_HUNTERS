package com.intellias.intellistart.interviewplanning.controller;

import com.intellias.intellistart.interviewplanning.controller.dto.BookingDto;
import com.intellias.intellistart.interviewplanning.controller.dto.DashboardDto;
import com.intellias.intellistart.interviewplanning.controller.dto.InterviewerSlotDto;
import com.intellias.intellistart.interviewplanning.controller.dto.mapper.BookingMapper;
import com.intellias.intellistart.interviewplanning.controller.dto.mapper.InterviewerSlotsMapper;
import com.intellias.intellistart.interviewplanning.model.Booking;
import com.intellias.intellistart.interviewplanning.model.InterviewerSlot;
import com.intellias.intellistart.interviewplanning.model.User;
import com.intellias.intellistart.interviewplanning.service.BookingService;
import com.intellias.intellistart.interviewplanning.service.CoordinatorService;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 * CoordinatorController controller.
 */
@RestController
@AllArgsConstructor
public class CoordinatorController {

  private final CoordinatorService coordinatorService;
  private final BookingService bookingService;
  private final InterviewerSlotsMapper interviewerSlotsMapper;
  private final BookingMapper bookingMapper;

  @GetMapping("/users/interviewers")
  public List<User> getAllInterviewers() {
    return coordinatorService.getAllInterviewers();
  }

  @GetMapping("/users/coordinators")
  public List<User> getAllCoordinators() {
    return coordinatorService.getAllCoordinators();
  }

  @GetMapping("/weeks/{weekId}/dashboard")
  public DashboardDto getDashboardForWeek(@PathVariable int weekId) {
    return coordinatorService.getDashboardForWeek(weekId);
  }

  /**
   * Returns InterviewerSlotDto relative to {@code slot}.
   *
   * @param interviewerId slot owner interviewerId
   * @param slotId        id of interviewer slot
   * @param slot          new interviewerSlot
   * @return interviewerSlotDto as updated slot
   */
  @PostMapping("/interviewers/{interviewerId}/slots/{slotId}")
  public InterviewerSlotDto editSlot(@PathVariable Long interviewerId, @PathVariable Long slotId,
      @RequestBody InterviewerSlotDto slot) {
    InterviewerSlot entity = interviewerSlotsMapper.mapToInterviewerSlotEntity(slot);

    InterviewerSlot responseEntity = coordinatorService.editSlot(interviewerId, slotId, entity);
    return interviewerSlotsMapper.mapToInterviewerSlotsDto(responseEntity);
  }

  /**
   * Handles POST requests and creates booking.
   */
  @PostMapping("/bookings")
  @ResponseStatus(HttpStatus.CREATED)
  public BookingDto createBooking(@RequestBody BookingDto bookingDto) {
    Booking booking = bookingMapper.createBookingFromDto(bookingDto);

    Booking responseEntity = bookingService.createBooking(booking,
        bookingDto.getInterviewerSlotId(),
        bookingDto.getCandidateSlotId());
    return bookingMapper.createBookingDto(responseEntity);
  }

  @PostMapping("/bookings/{bookingId}")
  @ResponseStatus(HttpStatus.OK)
  public BookingDto updateBooking(@PathVariable Long bookingId, @RequestBody BookingDto booking) {
    Booking bookingEntity = bookingMapper.createBookingFromDto(booking);

    Booking responseEntity = coordinatorService.updateBooking(
        bookingId,
        booking.getCandidateSlotId(),
        booking.getInterviewerSlotId(),
        bookingEntity);

    return bookingMapper.createBookingDto(responseEntity);
  }

  /**
   * Handles DELETE requests and deletes booking by id.
   */
  @DeleteMapping("/bookings/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void deleteBooking(@PathVariable Long id) {
    bookingService.deleteBooking(id);
  }
}
