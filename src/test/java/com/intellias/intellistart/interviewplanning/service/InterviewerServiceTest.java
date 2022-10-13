package com.intellias.intellistart.interviewplanning.service;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import com.intellias.intellistart.interviewplanning.model.InterviewerSlot;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class InterviewerServiceTest {
  private final InterviewerService serviceMock = Mockito.mock(InterviewerService.class);

  @Test
  void createSlot() {
    when(serviceMock.createSlot()).thenReturn(new InterviewerSlot());
    final InterviewerSlot newSlot = serviceMock.createSlot();

    assertNotNull(newSlot);
  }

  @Test
  void editSlot() {
    when(serviceMock.editSlot()).thenReturn(true);
    final boolean result = serviceMock.editSlot();

    assertTrue(result);
  }

  @Test
  void getAllSlots() {
    final List<InterviewerSlot> result = serviceMock.getAllSlots();

    assertNotNull(result);
  }

  @Test
  void setMaxBookings() {
    serviceMock.setMaxBookings();
  }
}