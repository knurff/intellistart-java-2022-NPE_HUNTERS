package com.intellias.intellistart.interviewplanning.controller;

import com.intellias.intellistart.interviewplanning.model.User;
import com.intellias.intellistart.interviewplanning.service.BookingService;
import com.intellias.intellistart.interviewplanning.service.CoordinatorService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 * CoordinatorController controller.
 */
@RestController
public class CoordinatorController {

  private final CoordinatorService coordinatorService;
  private final BookingService bookingService;

  @Autowired
  public CoordinatorController(CoordinatorService coordinatorService,
      BookingService bookingService) {
    this.coordinatorService = coordinatorService;
    this.bookingService = bookingService;
  }

  @GetMapping("/users/interviewers")
  public List<User> getAllInterviewers() {
    return coordinatorService.getAllInterviewers();
  }

  @GetMapping("/users/coordinators")
  public List<User> getAllCoordinators() {
    return coordinatorService.getAllCoordinators();
  }

  @DeleteMapping("/bookings/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void deleteBooking(@PathVariable Long id) {
    bookingService.deleteBooking(id);
  }
}
