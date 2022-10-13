package com.intellias.intellistart.interviewplanning.model.dayofweek;

import com.intellias.intellistart.interviewplanning.exception.InvalidDayOfWeekException;

/**
 * DayOfWeek enum.
 */
public enum DayOfWeek {
  MONDAY("Mon"),
  TUESDAY("Tue"),
  WEDNESDAY("Wed"),
  THURSDAY("Thu"),
  FRIDAY("Fri");

  private final String day;

  DayOfWeek(String day) {
    this.day = day;
  }

  public String getDay() {
    return day;
  }

  /**
   * Returns enum dayOfWeek.
   *
   * @param text string day name
   * @return enum DayOfWeek
   * @throws InvalidDayOfWeekException if {@code text} is not in DayOfWeek
   */
  public static DayOfWeek fromString(String text) {
    for (DayOfWeek d : DayOfWeek.values()) {
      if (d.day.equalsIgnoreCase(text)) {
        return d;
      }
    }
    throw new InvalidDayOfWeekException("Invalid day of week: " + text);

  }
}
