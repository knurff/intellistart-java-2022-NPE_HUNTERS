package com.intellias.intellistart.interviewplanning.service;

import com.intellias.intellistart.interviewplanning.model.Booking;
import com.intellias.intellistart.interviewplanning.model.CandidateSlot;
import com.intellias.intellistart.interviewplanning.repository.CandidateSlotRepository;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * CandidateService service.
 */
@Service
public class CandidateService {

  @Autowired
  private CandidateSlotRepository candidateSlotRepository;


  public CandidateSlot createSlot() {
    return new CandidateSlot();
  }

  public boolean editSlot() {
    return true;
  }

  public List<CandidateSlot> getAllSlots() {
    return new ArrayList<>();
  }


}

