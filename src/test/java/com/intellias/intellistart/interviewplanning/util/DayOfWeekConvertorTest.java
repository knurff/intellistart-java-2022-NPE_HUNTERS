package com.intellias.intellistart.interviewplanning.util;

import static org.junit.jupiter.api.Assertions.*;

import com.intellias.intellistart.interviewplanning.exception.InvalidDayOfWeekException;
import java.time.DayOfWeek;
import java.util.Locale;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class DayOfWeekConvertorTest {

  @Test
  void stringToDayOfWeekThrowsAnExceptionIfInvalidDayProvided() {
    String day = "Magicday";
    String expectedMessage = "Invalid day of week: " + day;

    Exception e = assertThrows(InvalidDayOfWeekException.class,
        () -> DayOfWeekConvertor.stringToDayOfWeek(day));

    assertEquals(expectedMessage, e.getMessage());
  }

  @ParameterizedTest
  @CsvSource({
      "Monday",
      "Tuesday",
      "Wednesday",
      "Thursday",
      "Friday",
      "Saturday",
      "Sunday"
  })
  void stringToDayOfWeekWorksProperly(String day) {
    DayOfWeek dayOfWeek = DayOfWeekConvertor.stringToDayOfWeek(day);

    assertEquals(dayOfWeek.toString(), day.toUpperCase(Locale.ROOT));
  }
}