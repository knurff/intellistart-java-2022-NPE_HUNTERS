package com.intellias.intellistart.interviewplanning.util;

import com.intellias.intellistart.interviewplanning.model.CandidateSlot;
import com.intellias.intellistart.interviewplanning.model.TimePeriod;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;

public class CandidateSlotFactory {

  private static final String TEST_EMAIL = "test@gmail.com";

  public static CandidateSlot createSlotWithDateInPast() {
    return new CandidateSlot(TEST_EMAIL, LocalDate.now().minusDays(1),
        new TimePeriod(LocalTime.of(12, 30), LocalTime.of(15, 30)));
  }

  public static CandidateSlot createSlotWithTimePeriodInPast() {
    return new CandidateSlot(TEST_EMAIL, LocalDate.now(),
        new TimePeriod(LocalTime.now().minusHours(1).truncatedTo(ChronoUnit.HOURS),
            LocalTime.now().plusHours(1).truncatedTo(ChronoUnit.HOURS)));
  }

  public static CandidateSlot createSlotWithDurationLessThanMin() {
    return new CandidateSlot(TEST_EMAIL, LocalDate.now().plusDays(1),
        new TimePeriod(LocalTime.of(15, 30), LocalTime.of(16, 30)));
  }

  public static CandidateSlot createSlotWithNotRoundedPeriod() {
    return new CandidateSlot(TEST_EMAIL, LocalDate.now().plusDays(1),
        new TimePeriod(LocalTime.of(10, 16), LocalTime.of(12, 30)));
  }

  public static CandidateSlot createCandidateSlot() {
    return new CandidateSlot(TEST_EMAIL, LocalDate.now().plusDays(1),
        new TimePeriod(LocalTime.of(10, 30), LocalTime.of(15, 30)));
  }

  public static CandidateSlot createCandidateSlotWithAnonymousEmail() {
    return new CandidateSlot("anonymous", LocalDate.now().plusDays(1),
        new TimePeriod(LocalTime.of(10, 30), LocalTime.of(15, 30)));
  }
}
