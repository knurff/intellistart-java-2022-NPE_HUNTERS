package com.intellias.intellistart.interviewplanning.model;

import java.time.LocalDate;

/**
 * Interface, that declares generic behavior for CandidateSlot and InterviewerSlot.
 */
public interface Slot {

  Long getId();

  LocalDate getDate();

  TimePeriod getPeriod();
}
