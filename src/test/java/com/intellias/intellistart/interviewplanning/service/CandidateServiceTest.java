package com.intellias.intellistart.interviewplanning.service;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import com.intellias.intellistart.interviewplanning.model.CandidateSlot;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class CandidateServiceTest {
  private final CandidateService serviceMock = Mockito.mock(CandidateService.class);

  @Test
  void createSlot() {
    when(serviceMock.createSlot()).thenReturn(new CandidateSlot());
    final CandidateSlot newSlot = serviceMock.createSlot();

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
    final List<CandidateSlot> result = serviceMock.getAllSlots();

    assertNotNull(result);
  }
}