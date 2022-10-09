package com.intellias.intellistart.interviewplanning.service;

import com.intellias.intellistart.interviewplanning.model.InterviewerSlot;
import com.intellias.intellistart.interviewplanning.repository.InterviewerSlotRepository;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * InterviewerService service.
 */
@Service
public class InterviewerService {

  @Autowired
  private InterviewerSlotRepository interviewerSlotRepository;

  public InterviewerSlot createSlot() {
    return new InterviewerSlot();
  }

  public boolean editSlot() {
    return true;
  }

  public List<InterviewerSlot> getAllSlots() {
    return new ArrayList<>();
  }

  public void setMaxBookings() {

  }
}
