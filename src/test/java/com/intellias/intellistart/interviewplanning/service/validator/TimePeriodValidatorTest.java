package com.intellias.intellistart.interviewplanning.service.validator;

import static org.junit.jupiter.api.Assertions.*;

import com.intellias.intellistart.interviewplanning.exception.InvalidBookingDurationException;
import com.intellias.intellistart.interviewplanning.exception.InvalidTimePeriodBoundaries;
import com.intellias.intellistart.interviewplanning.model.TimePeriod;
import java.time.LocalTime;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class TimePeriodValidatorTest {

  @ParameterizedTest
  @CsvSource({"13:00, 11:00", "23:02, 23:00"})
  void checkTimePeriodWithWorkingHoursAndDurationMoreThanMinIfEndTimeBeforeStartTime(
      LocalTime startTime,
      LocalTime endTime
  ) {
    TimePeriod period = new TimePeriod(startTime, endTime);
    String expectedMessage
        = "Start time : " + startTime + " is later then end time :  " + endTime;

    Exception e = assertThrows(InvalidTimePeriodBoundaries.class,
        () -> TimePeriodValidator.checkTimePeriodWithWorkingHoursAndDurationMoreThanMin(period));

    assertEquals(expectedMessage, e.getMessage());
  }

  @ParameterizedTest
  @CsvSource({"13:00, 14:35", "17:05, 19:00"})
  void checkTimePeriodWithWorkingHoursAndDurationMoreThanMinIfTimeNotRound(
      LocalTime startTime,
      LocalTime endTime
  ) {
    TimePeriod period = new TimePeriod(startTime, endTime);
    String expectedMessage = "Time period has to be rounded to 30 minutes";

    Exception e = assertThrows(InvalidTimePeriodBoundaries.class,
        () -> TimePeriodValidator.checkTimePeriodWithWorkingHoursAndDurationMoreThanMin(period));

    assertEquals(expectedMessage, e.getMessage());
  }

  @ParameterizedTest
  @CsvSource({"07:00, 08:30", "21:00, 22:30"})
  void checkTimePeriodWithWorkingHoursAndDurationMoreThanMinIfPeriodIsNotInWorkingHours(
      LocalTime startTime,
      LocalTime endTime
  ) {
    TimePeriod period = new TimePeriod(startTime, endTime);
    String expectedMessage = " Working time  is (8:00-22:00)";

    Exception e = assertThrows(InvalidTimePeriodBoundaries.class,
        () -> TimePeriodValidator.checkTimePeriodWithWorkingHoursAndDurationMoreThanMin(period));

    assertEquals(expectedMessage, e.getMessage());
  }

  @ParameterizedTest
  @CsvSource({"08:00, 09:00", "21:00, 21:00"})
  void checkTimePeriodWithWorkingHoursAndDurationMoreThanMinIfPeriodDurationLessThanMin(
      LocalTime startTime,
      LocalTime endTime
  ) {
    TimePeriod period = new TimePeriod(startTime, endTime);
    String expectedMessage = "Slot has to be 1,5 hours or more";

    Exception e = assertThrows(InvalidTimePeriodBoundaries.class,
        () -> TimePeriodValidator.checkTimePeriodWithWorkingHoursAndDurationMoreThanMin(period));

    assertEquals(expectedMessage, e.getMessage());
  }

  @ParameterizedTest
  @CsvSource({"08:00, 09:30", "20:00, 22:00"})
  void checkTimePeriodWithWorkingHoursAndDurationMoreThanMinWorksProperly(
      LocalTime startTime,
      LocalTime endTime
  ) {
    TimePeriod period = new TimePeriod(startTime, endTime);

    try {
      TimePeriodValidator.checkTimePeriodWithWorkingHoursAndDurationMoreThanMin(period);
    } catch (Exception e) {
      fail("This method should not throw an exception on given input");
    }
  }

  @ParameterizedTest
  @CsvSource({"13:00, 11:30", "23:02, 23:00"})
  void checkTimePeriodWithoutWorkingHoursIfEndTimeBeforeStartTime(
      LocalTime startTime,
      LocalTime endTime
  ) {
    TimePeriod period = new TimePeriod(startTime, endTime);
    String expectedMessage1
        = "Start time : " + startTime + " is later then end time :  " + endTime;

    Exception e = assertThrows(InvalidTimePeriodBoundaries.class,
        () -> TimePeriodValidator.checkTimePeriodWithoutWorkingHours(period));

    assertEquals(expectedMessage1, e.getMessage());
  }

  @ParameterizedTest
  @CsvSource({"12:00, 13:35", "17:05, 19:00"})
  void checkTimePeriodWithoutWorkingHoursIfTimeNotRound(
      LocalTime startTime,
      LocalTime endTime
  ) {
    TimePeriod period = new TimePeriod(startTime, endTime);
    String expectedMessage = "Time period has to be rounded to 30 minutes";

    Exception e = assertThrows(InvalidTimePeriodBoundaries.class,
        () -> TimePeriodValidator.checkTimePeriodWithoutWorkingHours(period));

    assertEquals(expectedMessage, e.getMessage());
  }

  @ParameterizedTest
  @CsvSource({"12:00, 14:00", "17:00, 17:00"})
  void checkTimePeriodWithoutWorkingHoursIfDurationIsNotEqualToNinetyMinutes(
      LocalTime startTime,
      LocalTime endTime
  ) {
    TimePeriod period = new TimePeriod(startTime, endTime);
    String expectedMessage = "Booking time has to be 1,5 hours";

    Exception e = assertThrows(InvalidBookingDurationException.class,
        () -> TimePeriodValidator.checkTimePeriodWithoutWorkingHours(period));

    assertEquals(expectedMessage, e.getMessage());
  }

  @ParameterizedTest
  @CsvSource({"12:00, 13:30", "13:30, 15:00"})
  void checkTimePeriodWithoutWorkingHoursWorksProperly(
      LocalTime startTime,
      LocalTime endTime
  ) {
    TimePeriod period = new TimePeriod(startTime, endTime);

    try {
      TimePeriodValidator.checkTimePeriodWithoutWorkingHours(period);
    } catch (Exception e) {
      fail("This method should not throw an exception on given input");
    }
  }

  @ParameterizedTest
  @CsvSource({"13:00, 11:30", "23:02, 23:00"})
  void checkTimePeriodWithoutWorkingHoursAndDurationMoreThanMinIfEndTimeBeforeStartTime(
      LocalTime startTime,
      LocalTime endTime
  ) {
    TimePeriod period = new TimePeriod(startTime, endTime);
    String expectedMessage1
        = "Start time : " + startTime + " is later then end time :  " + endTime;

    Exception e = assertThrows(InvalidTimePeriodBoundaries.class,
        () -> TimePeriodValidator.checkTimePeriodWithoutWorkingHoursAndDurationMoreThanMin(period)
    );

    assertEquals(expectedMessage1, e.getMessage());
  }

  @ParameterizedTest
  @CsvSource({"12:00, 13:35", "17:05, 19:00"})
  void checkTimePeriodWithoutWorkingHoursAndDurationMoreThanMinIfTimeNotRound(
      LocalTime startTime,
      LocalTime endTime
  ) {
    TimePeriod period = new TimePeriod(startTime, endTime);
    String expectedMessage = "Time period has to be rounded to 30 minutes";

    Exception e = assertThrows(InvalidTimePeriodBoundaries.class,
        () -> TimePeriodValidator.checkTimePeriodWithoutWorkingHoursAndDurationMoreThanMin(period)
    );

    assertEquals(expectedMessage, e.getMessage());
  }

  @ParameterizedTest
  @CsvSource({"08:00, 09:00", "17:00, 17:00"})
  void checkTimePeriodWithoutWorkingHoursAndDurationMoreThanMinIfPeriodDurationLessThanMin(
      LocalTime startTime,
      LocalTime endTime
  ) {
    TimePeriod period = new TimePeriod(startTime, endTime);
    String expectedMessage = "Slot has to be 1,5 hours or more";

    Exception e = assertThrows(InvalidTimePeriodBoundaries.class,
        () -> TimePeriodValidator.checkTimePeriodWithoutWorkingHoursAndDurationMoreThanMin(period)
    );

    assertEquals(expectedMessage, e.getMessage());
  }

  @ParameterizedTest
  @CsvSource({"12:00, 14:30", "14:30, 16:00"})
  void checkTimePeriodWithoutWorkingHoursAndDurationMoreThanMinWorksProperly(
      LocalTime startTime,
      LocalTime endTime
  ) {
    TimePeriod period = new TimePeriod(startTime, endTime);

    try {
      TimePeriodValidator.checkTimePeriodWithoutWorkingHoursAndDurationMoreThanMin(period);
    } catch (Exception e) {
      fail("This method should not throw an exception on given input");
    }
  }

  @ParameterizedTest
  @CsvSource({
    "12:00, 14:00, 13:00, 13:30",
    "15:00, 16:30, 15:00, 16:30",
    "15:00, 17:30, 15:00, 16:30",
    "15:00, 16:30, 15:30, 16:30",
    "15:00, 16:30, 16:00, 17:00",
    "15:00, 16:30, 15:30, 17:00"
  })
  void isOverlappingReturnsTrue(
      LocalTime startTime1,
      LocalTime endTime1,
      LocalTime startTime2,
      LocalTime endTime2
  ) {
    TimePeriod period1 = new TimePeriod(startTime1, endTime1);
    TimePeriod period2 = new TimePeriod(startTime2, endTime2);

    boolean result = TimePeriodValidator.isOverlapping(period1, period2);
    assertTrue(result);
  }

  @ParameterizedTest
  @CsvSource({
    "12:00, 13:00, 14:00, 15:00",
    "14:00, 15:00, 12:00, 13:00",
    "12:00, 13:00, 13:00, 14:00",
    "14:00, 15:00, 13:00, 14:00"
  })
  void isOverlappingReturnsFalse(
      LocalTime startTime1,
      LocalTime endTime1,
      LocalTime startTime2,
      LocalTime endTime2
  ) {
    TimePeriod period1 = new TimePeriod(startTime1, endTime1);
    TimePeriod period2 = new TimePeriod(startTime2, endTime2);

    boolean result = TimePeriodValidator.isOverlapping(period1, period2);
    assertFalse(result);
  }
}