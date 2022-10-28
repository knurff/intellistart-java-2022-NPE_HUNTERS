package com.intellias.intellistart.interviewplanning.service.validator;

import com.intellias.intellistart.interviewplanning.exception.InvalidBookingDurationException;
import com.intellias.intellistart.interviewplanning.exception.InvalidTimePeriodBoundaries;
import com.intellias.intellistart.interviewplanning.model.TimePeriod;
import com.intellias.intellistart.interviewplanning.util.TimeConverter;
import java.time.LocalTime;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * TimePeriodValidator class, which contains methods for validate time period boundaries.
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class TimePeriodValidator {

  private static final int MINUTE_ROUND = 30;
  private static final float MIN_TIME_PERIOD_DURATION = 90;
  private static final int BOOKING_DURATION_IN_MINUTES = 90;
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
    checkTimePeriodDurationMoreThanMin(startTime, endTime);
  }

  /**
   * Checks TimePeriod boundaries without working hours and validates, that duration is equal to 1,5
   * hours.
   */
  public static void checkTimePeriodWithoutWorkingHours(TimePeriod period) {
    LocalTime startTime = period.getStartTime();
    LocalTime endTime = period.getEndTime();

    checkStartTimeIsBeforeEndTime(startTime, endTime);
    checkTimePeriodRound(startTime, endTime);
    checkTimePeriodDurationIsEqualToNinetyMinutes(startTime, endTime);
  }

  public static boolean isOverlapping(TimePeriod period1, TimePeriod period2) {
    return period1.getStartTime().compareTo(period2.getEndTime()) <= 0
        && period1.getEndTime().compareTo(period2.getStartTime()) >= 0;
  }

  private static void checkStartTimeIsBeforeEndTime(LocalTime startTime, LocalTime endTime) {
    if (startTime.isAfter(endTime)) {
      throw new InvalidTimePeriodBoundaries(
          "Start time : " + startTime + " is later then end time :  " + endTime);
    }
  }

  private static void checkTimePeriodRound(LocalTime startTime, LocalTime endTime) {
    if (startTime.getMinute() % MINUTE_ROUND != 0 || endTime.getMinute() % MINUTE_ROUND != 0) {
      throw new InvalidTimePeriodBoundaries("Time period has to be rounded to 30 minutes");
    }
  }

  private static void checkTimePeriodIsIsWorkingHours(LocalTime startTime, LocalTime endTime) {
    if (startTime.isBefore(START_WORK_HOURS) || endTime.isAfter(END_WORK_HOURS)) {
      throw new InvalidTimePeriodBoundaries(" Working time  is (8:00-22:00)");
    }
  }

  private static void checkTimePeriodDurationMoreThanMin(LocalTime startTime,
      LocalTime endTime) {
    int startTimeMinutes = TimeConverter.convertTimeToMinutes(startTime);
    int endTimeMinutes = TimeConverter.convertTimeToMinutes(endTime);

    if (endTimeMinutes - startTimeMinutes < MIN_TIME_PERIOD_DURATION) {
      throw new InvalidTimePeriodBoundaries("Slot has to be 1,5 hours or more");
    }
  }

  private static void checkTimePeriodDurationIsEqualToNinetyMinutes(LocalTime startTime,
      LocalTime endTime) {
    int startTimeMinutes = TimeConverter.convertTimeToMinutes(startTime);
    int endTimeMinutes = TimeConverter.convertTimeToMinutes(endTime);

    if (endTimeMinutes - startTimeMinutes != BOOKING_DURATION_IN_MINUTES) {
      throw new InvalidBookingDurationException("Booking time has to be 1,5 hours");
    }
  }
}
