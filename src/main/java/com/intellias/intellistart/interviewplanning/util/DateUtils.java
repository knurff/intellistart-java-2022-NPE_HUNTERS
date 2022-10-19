package com.intellias.intellistart.interviewplanning.util;

import com.intellias.intellistart.interviewplanning.exception.InvalidTimeSlotBoundariesException;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalField;
import java.time.temporal.WeekFields;

/**
 * Util class, which contains methods for getting information about
 * current and next week numbers.
 */
public final class DateUtils {

  /**
   * Don't let anyone instantiate this class.
   */
  private DateUtils() {}

  /**
   * Default date from which numbering begins.
   */
  private static final LocalDate NUMBERING_FROM = LocalDate.of(2022, 1, 1);

  /**
   * Returns week number of {@code current}, relative to {@code from}.
   *
   * @param from the date from which numbering begins
   * @param current date week number of which we need to find
   * @return the week number(1-INT_MAX) of {@code current}
   * @throws IllegalArgumentException if {@code from} is greater than {@code current}
   */
  public static int getWeekFrom(LocalDate from, LocalDate current) {
    if (from.compareTo(current) > 0) {
      throw new IllegalArgumentException("'from' value must be less than current");
    }

    TemporalField fieldIso = WeekFields.ISO.dayOfWeek();

    return (int) ChronoUnit.WEEKS.between(from.with(fieldIso, 1), current) + 1;
  }

  /**
   * Returns next week number of {@code current}, relative to {@code from}.
   *
   * @param from the date from which numbering begins
   * @param current date next week number of which we need to find
   * @return the next week number(2-INT_MAX) of {@code current}
   * @throws IllegalArgumentException if {@code from} is greater than {@code current}
   */
  public static int getNextWeekFrom(LocalDate from, LocalDate current) {
    return getWeekFrom(from, current) + 1;
  }

  /**
   * Returns week number of today's date, relative to {@code NUMBERING_FROM}.
   *
   * @return the week number(1-INT_MAX)
   */
  public static int getCurrentWeek() {
    return getWeekFrom(NUMBERING_FROM, LocalDate.now());
  }

  /**
   * Returns next week number of today's date, relative to {@code NUMBERING_FROM}.
   *
   * @return the next week number(2-INT_MAX)
   */
  public static int getNextWeek() {
    return getNextWeekFrom(NUMBERING_FROM, LocalDate.now());
  }

  /**
   * Returns nothing.
   *
   * @param date date to check
   * @throws InvalidTimeSlotBoundariesException if {@code date} is before than current date
   */
  public static void checkDateIsInFuture(LocalDate date) {
    if (date.isBefore(LocalDate.now())) {
      throw new InvalidTimeSlotBoundariesException("Date must be in future");
    }
  }
}
