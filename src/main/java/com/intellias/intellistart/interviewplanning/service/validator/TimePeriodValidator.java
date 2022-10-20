package com.intellias.intellistart.interviewplanning.service.validator;

import com.intellias.intellistart.interviewplanning.exception.InvalidTimeSlotBoundariesException;
import com.intellias.intellistart.interviewplanning.model.TimePeriod;
import com.intellias.intellistart.interviewplanning.util.TimeConverter;
import java.time.LocalTime;

/**
 * TimePeriodValidator class, which contains methods for validate time period boundaries.
 */
public class TimePeriodValidator {

  private static final int MINUTE_ROUND = 30;
  private static final float MIN_TIME_PERIOD_DURATION = 90;
  private static final LocalTime START_WORK_HOURS = LocalTime.of(8, 0);
  private static final LocalTime END_WORK_HOURS = LocalTime.of(22, 0);

  /**
   * Check TimePeriod boundaries.
   *
   * @param period period to check
   */
  public static void checkTimePeriod(TimePeriod period) {
    LocalTime startTime = period.getStartTime();
    LocalTime endTime = period.getEndTime();

    checkStartTimeIsBeforeEndTime(startTime, endTime);
    checkTimePeriodRound(startTime, endTime);
    checkTimePeriodIsIsWorkingHours(startTime, endTime);
    checkTimePeriodDuration(startTime, endTime);

  }

  public static boolean isOverlapping(TimePeriod period1, TimePeriod period2) {
    return period1.getStartTime().compareTo(period2.getEndTime()) <= 0
        && period1.getEndTime().compareTo(period2.getStartTime()) >= 0;
  }

  private static void checkStartTimeIsBeforeEndTime(LocalTime startTime, LocalTime endTime) {
    if (startTime.isAfter(endTime)) {
      throw new InvalidTimeSlotBoundariesException(
          "Start time : " + startTime + " is later then end time :  " + endTime);
    }
  }

  private static void checkTimePeriodRound(LocalTime startTime, LocalTime endTime) {
    if (startTime.getMinute() % MINUTE_ROUND != 0 || endTime.getMinute() % MINUTE_ROUND != 0) {
      throw new InvalidTimeSlotBoundariesException("Slot has to be rounded to 30 minutes");
    }
  }

  private static void checkTimePeriodIsIsWorkingHours(LocalTime startTime, LocalTime endTime) {
    if (startTime.isBefore(START_WORK_HOURS) || endTime.isAfter(END_WORK_HOURS)) {
      throw new InvalidTimeSlotBoundariesException(" Working time  is (8:00-22:00)");
    }
  }

  private static void checkTimePeriodDuration(LocalTime startTime, LocalTime endTime) {
    int startTimeMinutes = TimeConverter.convertTimeToMinutes(startTime);
    int endTimeMinutes = TimeConverter.convertTimeToMinutes(endTime);

    if (endTimeMinutes - startTimeMinutes < MIN_TIME_PERIOD_DURATION) {
      throw new InvalidTimeSlotBoundariesException("Slot has to be 1,5 hours or more");
    }
  }
}
