package com.intellias.intellistart.interviewplanning.service;

import static org.junit.jupiter.api.Assertions.*;

import com.intellias.intellistart.interviewplanning.model.CandidateSlot;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class CandidateServiceTest {

  private static CandidateService service;

  @BeforeAll
  static void init() {
    service = new CandidateService();
  }

  @Test
  void createSlot() {
    final CandidateSlot newSlot = service.createSlot();

    assertNotNull(newSlot);
  }

  @Test
  void editSlot() {
    final boolean result = service.editSlot();

    assertTrue(result);
  }

  @Test
  void getAllSlots() {
    final List<CandidateSlot> result = service.getAllSlots();

    assertNotNull(result);
  }
}