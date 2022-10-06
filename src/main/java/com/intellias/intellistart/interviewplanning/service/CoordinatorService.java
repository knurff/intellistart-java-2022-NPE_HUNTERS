package com.intellias.intellistart.interviewplanning.service;

import com.intellias.intellistart.interviewplanning.model.Booking;
import com.intellias.intellistart.interviewplanning.model.Slot;
import com.intellias.intellistart.interviewplanning.model.User;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;

/**
 * CoordinatorService service.
 */
@Service
public class CoordinatorService {
  public Booking createBooking() {
    return new Booking();
  }

  public boolean editSlot() {
    return true;
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
}
