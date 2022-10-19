package com.intellias.intellistart.interviewplanning.util;

import com.intellias.intellistart.interviewplanning.exception.InvalidDayOfWeekException;
import java.time.DayOfWeek;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.TemporalAccessor;


/**
 * DayOfWeekConvertor convertor class.
 */
public class DayOfWeekConvertor {


  /**
   * Returns DayOfWeek enum of {@code day}.
   *
   * @param day string with day name
   * @return DayOfWeek enum
   * @throws InvalidDayOfWeekException if {@code day} is not a dayOfWeek name
   */
  public static DayOfWeek stringToDayOfWeek(String day) {
    try {
      DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEEE");
      TemporalAccessor accessor = formatter.parse(day);
      return DayOfWeek.from(accessor);
    } catch (DateTimeParseException e) {
      throw new InvalidDayOfWeekException("Invalid day of week: " + day);
    }
  }

}
