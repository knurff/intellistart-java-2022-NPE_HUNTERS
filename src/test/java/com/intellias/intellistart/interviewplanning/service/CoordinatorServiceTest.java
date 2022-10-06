package com.intellias.intellistart.interviewplanning.service;

import static org.junit.jupiter.api.Assertions.*;

import com.intellias.intellistart.interviewplanning.model.Booking;
import com.intellias.intellistart.interviewplanning.model.Slot;
import com.intellias.intellistart.interviewplanning.model.User;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class CoordinatorServiceTest {

  private static CoordinatorService service;

  @BeforeAll
  static void init() {
    service = new CoordinatorService();
  }

  @Test
  void createBooking() {
    final Booking newBooking = service.createBooking();

    assertNotNull(newBooking);
  }

  @Test
  void editSlot() {
    final boolean result = service.editSlot();

    assertTrue(result);
  }

  @Test
  void editBooking() {
    final boolean result = service.editBooking();

    assertTrue(result);
  }

  @Test
  void deleteBooking() {
    final boolean result = service.deleteBooking();

    assertTrue(result);
  }

  @Test
  void grantRoleForUser() {
    final boolean result = service.grantRoleForUser();

    assertTrue(result);
  }

  @Test
  void removeRoleFromUser() {
    final boolean result = service.removeRoleFromUser();

    assertTrue(result);
  }

  @Test
  void getAllUsersSlots() {
    final List<Slot> result = service.getAllUsersSlots();

    assertNotNull(result);
  }

  @Test
  void getUsersByRole() {
    final List<User> result = service.getUsersByRole();

    assertNotNull(result);
  }
}