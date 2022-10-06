package com.intellias.intellistart.interviewplanning.service;

import static org.junit.jupiter.api.Assertions.*;

import com.intellias.intellistart.interviewplanning.model.Slot;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class InterviewerServiceTest {

  private static InterviewerService service;

  @BeforeAll
  static void init() {
    service = new InterviewerService();
  }

  @Test
  void createSlot() {
    final Slot newSlot = service.createSlot();

    assertNotNull(newSlot);
  }

  @Test
  void editSlot() {
    final boolean result = service.editSlot();

    assertTrue(result);
  }

  @Test
  void getAllSlots() {
    final List<Slot> result = service.getAllSlots();

    assertNotNull(result);
  }

  @Test
  void setMaxBookings() {
    service.setMaxBookings();
  }
}