package com.intellias.intellistart.interviewplanning.controller.dto;

import com.intellias.intellistart.interviewplanning.model.Booking;
import com.intellias.intellistart.interviewplanning.model.CandidateSlot;
import com.intellias.intellistart.interviewplanning.model.InterviewerSlot;
import java.util.Map;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DashboardDayDto {
  private final Map<InterviewerSlot, Set<Long>> bookingIdsByInterviewerSlot;
  private final Map<CandidateSlot, Set<Long>> bookingIdsByCandidateSlot;
  private final Map<Long, Booking> bookingsByIds;
}
