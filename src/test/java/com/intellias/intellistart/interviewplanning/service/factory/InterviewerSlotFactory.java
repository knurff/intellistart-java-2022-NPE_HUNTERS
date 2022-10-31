package com.intellias.intellistart.interviewplanning.service.factory;

import com.intellias.intellistart.interviewplanning.model.InterviewerSlot;
import com.intellias.intellistart.interviewplanning.model.TimePeriod;
import com.intellias.intellistart.interviewplanning.util.DateUtils;
import java.time.DayOfWeek;
import java.time.LocalTime;

public class InterviewerSlotFactory {

  public static InterviewerSlot createSlotWithDateInPast() {
    InterviewerSlot slot = new InterviewerSlot();
    slot.setWeek(41);
    slot.setDayOfWeek(DayOfWeek.THURSDAY);
    slot.setPeriod(new TimePeriod(LocalTime.of(15, 0), LocalTime.of(17, 0)));

    return slot;
  }

  public static InterviewerSlot createSlotWithDurationLessThanMin() {
    InterviewerSlot slot = new InterviewerSlot();
    slot.setWeek(DateUtils.getNextWeek());
    slot.setDayOfWeek(DayOfWeek.THURSDAY);
    slot.setPeriod(new TimePeriod(LocalTime.of(15, 0), LocalTime.of(15, 30)));
    return slot;
  }

  public static InterviewerSlot createSlotWithNotRoundedPeriod() {
    InterviewerSlot slot = new InterviewerSlot();
    slot.setWeek(DateUtils.getNextWeek());
    slot.setDayOfWeek(DayOfWeek.THURSDAY);
    slot.setPeriod(new TimePeriod(LocalTime.of(15, 0), LocalTime.of(17, 2)));

    return slot;
  }

  public static InterviewerSlot createSlotWithTimePeriodInNotWorkingHours() {
    InterviewerSlot slot = new InterviewerSlot();
    slot.setWeek(DateUtils.getNextWeek());
    slot.setDayOfWeek(DayOfWeek.THURSDAY);
    slot.setPeriod(new TimePeriod(LocalTime.of(7, 0), LocalTime.of(17, 0)));

    return slot;
  }

  public static InterviewerSlot createSlotWithDateNotOnTheNextWeek() {
    InterviewerSlot slot = new InterviewerSlot();
    slot.setWeek(DateUtils.getNextWeek() + 2);
    slot.setDayOfWeek(DayOfWeek.THURSDAY);
    slot.setPeriod(new TimePeriod(LocalTime.of(14, 0), LocalTime.of(17, 0)));

    return slot;
  }


  public static InterviewerSlot createInterviewerSlot() {
    InterviewerSlot slot = new InterviewerSlot();
    slot.setWeek(DateUtils.getNextWeek());
    slot.setDayOfWeek(DayOfWeek.THURSDAY);
    slot.setPeriod(new TimePeriod(LocalTime.of(14, 0), LocalTime.of(19, 0)));

    return slot;
  }


  public static InterviewerSlot createAnotherInterviewerSlot() {
    InterviewerSlot slot = new InterviewerSlot();
    slot.setWeek(DateUtils.getNextWeek());
    slot.setDayOfWeek(DayOfWeek.WEDNESDAY);
    slot.setPeriod(new TimePeriod(LocalTime.of(9, 0), LocalTime.of(17, 0)));

    return slot;

  }
}
