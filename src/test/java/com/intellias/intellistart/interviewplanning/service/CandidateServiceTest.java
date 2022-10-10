package com.intellias.intellistart.interviewplanning.service;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.intellias.intellistart.interviewplanning.model.CandidateSlot;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

class CandidateServiceTest {

  @Autowired
  private CandidateService service = new CandidateService();

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