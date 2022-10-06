package com.intellias.intellistart.interviewplanning.service;

import com.intellias.intellistart.interviewplanning.model.Slot;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;

/**
 * CandidateService service.
 */
@Service
public class CandidateService {
  public Slot createSlot() {
    return new Slot();
  }

  public boolean editSlot() {
    return true;
  }

  public List<Slot> getAllSlots() {
    return new ArrayList<>();
  }
}
