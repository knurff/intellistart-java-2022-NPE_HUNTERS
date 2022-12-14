package com.intellias.intellistart.interviewplanning.controller;

import com.intellias.intellistart.interviewplanning.controller.dto.BookingDto;
import com.intellias.intellistart.interviewplanning.controller.dto.DashboardDto;
import com.intellias.intellistart.interviewplanning.controller.dto.EmailDto;
import com.intellias.intellistart.interviewplanning.controller.dto.UserDto;
import com.intellias.intellistart.interviewplanning.controller.dto.mapper.BookingMapper;
import com.intellias.intellistart.interviewplanning.model.Booking;
import com.intellias.intellistart.interviewplanning.model.User;
import com.intellias.intellistart.interviewplanning.model.role.UserRole;
import com.intellias.intellistart.interviewplanning.service.BookingService;
import com.intellias.intellistart.interviewplanning.service.CoordinatorService;
import java.util.List;
import javax.annotation.security.RolesAllowed;
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
@RolesAllowed("ROLE_COORDINATOR")
public class CoordinatorController {

  private final CoordinatorService coordinatorService;
  private final BookingService bookingService;
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

  /**
   * Handles POST request and updates an existing booking record.
   *
   * @param bookingId  an id of booking to be updated
   * @param bookingDto updated version of booking specified by bookingId
   * @return updated version of booking
   */
  @PostMapping("/bookings/{bookingId}")
  @ResponseStatus(HttpStatus.OK)
  public BookingDto updateBooking(@PathVariable Long bookingId,
      @RequestBody BookingDto bookingDto) {
    Booking booking = bookingMapper.createBookingFromDto(bookingDto);

    Booking responseEntity = bookingService.updateBooking(booking,
        bookingId,
        bookingDto.getInterviewerSlotId(),
        bookingDto.getCandidateSlotId());
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


  /**
   * Handles POST request and grants COORDINATOR role for user by email.
   */
  @PostMapping("/users/coordinators")
  @ResponseStatus(HttpStatus.CREATED)
  public UserDto grantCoordinatorRole(@RequestBody EmailDto emailDto) {
    User savedCoordinator =
        coordinatorService.grantRoleForUser(emailDto.getEmail(), UserRole.COORDINATOR);

    return new UserDto(savedCoordinator.getEmail(),
        savedCoordinator.getRole(),
        savedCoordinator.getId());
  }


  /**
   * Handles POST request and grants INTERVIEWER role for user by email.
   */
  @PostMapping("/users/interviewers")
  @ResponseStatus(HttpStatus.CREATED)
  public UserDto grantInterviewerRole(@RequestBody EmailDto emailDto) {
    User savedInterviewer =
        coordinatorService.grantRoleForUser(emailDto.getEmail(), UserRole.INTERVIEWER);

    return new UserDto(savedInterviewer.getEmail(),
        savedInterviewer.getRole(),
        savedInterviewer.getId());
  }

  @DeleteMapping("/users/coordinators/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void revokeCoordinatorRole(@PathVariable Long id) {
    coordinatorService.revokeRoleFromUser(id, UserRole.COORDINATOR);
  }

  @DeleteMapping("/users/interviewers/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void revokeInterviewerRole(@PathVariable Long id) {
    coordinatorService.revokeRoleFromUser(id, UserRole.INTERVIEWER);
  }
}
