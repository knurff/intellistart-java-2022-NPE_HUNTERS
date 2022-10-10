package com.intellias.intellistart.interviewplanning.service;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.intellias.intellistart.interviewplanning.model.InterviewerSlot;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

class InterviewerServiceTest {

  @Autowired
  private InterviewerService service = new InterviewerService();

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