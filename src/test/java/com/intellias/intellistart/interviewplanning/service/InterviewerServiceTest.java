package com.intellias.intellistart.interviewplanning.service;

import static org.junit.jupiter.api.Assertions.*;

import com.intellias.intellistart.interviewplanning.model.InterviewerSlot;
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
    final InterviewerSlot newSlot = service.createSlot();

    assertNotNull(newSlot);
  }

  @Test
  void editSlot() {
    final boolean result = service.editSlot();

    assertTrue(result);
  }

  @Test
  void getAllSlots() {
    final List<InterviewerSlot> result = service.getAllSlots();

    assertNotNull(result);
  }

  @Test
  void setMaxBookings() {
    service.setMaxBookings();
  }
}