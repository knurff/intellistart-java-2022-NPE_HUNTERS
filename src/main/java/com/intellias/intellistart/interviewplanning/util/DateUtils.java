package com.intellias.intellistart.interviewplanning.util;

import com.intellias.intellistart.interviewplanning.exception.InvalidSlotDateException;
import com.intellias.intellistart.interviewplanning.exception.InvalidTimePeriodBoundaries;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalField;
import java.time.temporal.WeekFields;

/**
 * Util class, which contains methods for getting information about current and next week numbers.
 */
public final class DateUtils {

  /**
   * Default date from which numbering begins.
   */
  public static final LocalDate DEFAULT_NUMBERING_FROM = LocalDate.of(2022, 1, 1);

  private static final String INVALID_FROM_DATE_MESSAGE = "'from' value must be less than current";

  private static final String INVALID_WEEK_NUMBER_MESSAGE = "Week number specified must be >= 0";

  /**
   * Don't let anyone instantiate this class.
   */
  private DateUtils() {
  }

  /**
   * Returns week number of {@code current}, relative to {@code from}.
   *
   * @param from    the date from which numbering begins
   * @param current date week number of which we need to find
   * @return the week number(0-INT_MAX) of {@code current}
   * @throws IllegalArgumentException if {@code from} is greater than {@code current}
   */
  public static int getWeekFrom(LocalDate from, LocalDate current) {
    if (from.compareTo(current) > 0) {
      throw new IllegalArgumentException(INVALID_FROM_DATE_MESSAGE);
    }

    TemporalField fieldIso = WeekFields.ISO.dayOfWeek();

    return (int) ChronoUnit.WEEKS.between(from.with(fieldIso, 1), current);
  }

  /**
   * Returns next week number of {@code current}, relative to {@code from}.
   *
   * @param from    the date from which numbering begins
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
    return getWeekFrom(DEFAULT_NUMBERING_FROM, LocalDate.now());
  }

  /**
   * Returns next week number of today's date, relative to {@code NUMBERING_FROM}.
   *
   * @return the next week number(1-INT_MAX)
   */
  public static int getNextWeek() {
    return getNextWeekFrom(DEFAULT_NUMBERING_FROM, LocalDate.now());
  }

  /**
   * Returns nothing.
   *
   * @param date date to check
   * @throws InvalidTimePeriodBoundaries if {@code date} is before than current date
   */
  public static void checkDateIsInFuture(LocalDate date) {
    if (date.isBefore(LocalDate.now())) {
      throw new InvalidSlotDateException("Date must be in future");
    }
  }

  /**
   * Returns a LocalDate for a day of a week which (week) is specified by weekNumber.
   *
   * @param weekNumber a week's number starting from DEFAULT_NUMBERING_FROM
   * @param dayOfWeek  a day of the week specified for which to find the date
   * @return a LocalDate for a day of a week specified by weekNumber
   */
  public static LocalDate getDateOfDayOfWeek(final int weekNumber, final DayOfWeek dayOfWeek) {
    if (weekNumber < 0) {
      throw new IllegalArgumentException(INVALID_WEEK_NUMBER_MESSAGE);
    }

    LocalDate result = DEFAULT_NUMBERING_FROM.plusWeeks(weekNumber);

    while (result.getDayOfWeek().getValue() < dayOfWeek.getValue()) {
      result = result.plusDays(1);
    }

    while (result.getDayOfWeek().getValue() > dayOfWeek.getValue()) {
      result = result.minusDays(1);
    }

    return result;
  }
}