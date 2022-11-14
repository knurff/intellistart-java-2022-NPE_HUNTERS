package com.intellias.intellistart.interviewplanning.service.validator;

import static org.junit.jupiter.api.Assertions.*;

import com.intellias.intellistart.interviewplanning.exception.InvalidBookingDurationException;
import com.intellias.intellistart.interviewplanning.exception.InvalidTimePeriodBoundaries;
import com.intellias.intellistart.interviewplanning.model.TimePeriod;
import java.time.LocalTime;
import org.junit.jupiter.api.Test;

class TimePeriodValidatorTest {

  @Test
  void checkTimePeriodWithWorkingHoursAndDurationMoreThanMinIfEndTimeBeforeStartTime() {
    LocalTime startTime1 = LocalTime.of(13, 0);
    LocalTime endTime1 = LocalTime.of(11, 0);
    TimePeriod period1 = new TimePeriod(startTime1, endTime1);

    LocalTime startTime2 = LocalTime.of(23, 2);
    LocalTime endTime2 = LocalTime.of(23, 0);
    TimePeriod period2 = new TimePeriod(startTime2, endTime2);

    String expectedMessage1
        = "Start time : " + startTime1 + " is later then end time :  " + endTime1;

    String expectedMessage2
        = "Start time : " + startTime2 + " is later then end time :  " + endTime2;

    Exception e1 = assertThrows(InvalidTimePeriodBoundaries.class,
        () -> TimePeriodValidator.checkTimePeriodWithWorkingHoursAndDurationMoreThanMin(period1));

    Exception e2 = assertThrows(InvalidTimePeriodBoundaries.class,
        () -> TimePeriodValidator.checkTimePeriodWithWorkingHoursAndDurationMoreThanMin(period2));

    assertEquals(expectedMessage1, e1.getMessage());
    assertEquals(expectedMessage2, e2.getMessage());
  }

  @Test
  void checkTimePeriodWithWorkingHoursAndDurationMoreThanMinIfTimeNotRound() {
    LocalTime startTime1 = LocalTime.of(13, 0);
    LocalTime endTime1 = LocalTime.of(14, 35);
    TimePeriod period1 = new TimePeriod(startTime1, endTime1);

    LocalTime startTime2 = LocalTime.of(17, 5);
    LocalTime endTime2 = LocalTime.of(19, 0);
    TimePeriod period2 = new TimePeriod(startTime2, endTime2);

    String expectedMessage = "Time period has to be rounded to 30 minutes";

    Exception e1 = assertThrows(InvalidTimePeriodBoundaries.class,
        () -> TimePeriodValidator.checkTimePeriodWithWorkingHoursAndDurationMoreThanMin(period1));

    Exception e2 = assertThrows(InvalidTimePeriodBoundaries.class,
        () -> TimePeriodValidator.checkTimePeriodWithWorkingHoursAndDurationMoreThanMin(period2));

    assertEquals(expectedMessage, e1.getMessage());
    assertEquals(expectedMessage, e2.getMessage());
  }

  @Test
  void checkTimePeriodWithWorkingHoursAndDurationMoreThanMinIfPeriodIsNotInWorkingHours() {
    LocalTime startTime1 = LocalTime.of(7, 0);
    LocalTime endTime1 = LocalTime.of(8, 30);
    TimePeriod period1 = new TimePeriod(startTime1, endTime1);

    LocalTime startTime2 = LocalTime.of(21, 0);
    LocalTime endTime2 = LocalTime.of(22, 30);
    TimePeriod period2 = new TimePeriod(startTime2, endTime2);

    String expectedMessage = " Working time  is (8:00-22:00)";

    Exception e1 = assertThrows(InvalidTimePeriodBoundaries.class,
        () -> TimePeriodValidator.checkTimePeriodWithWorkingHoursAndDurationMoreThanMin(period1));

    Exception e2 = assertThrows(InvalidTimePeriodBoundaries.class,
        () -> TimePeriodValidator.checkTimePeriodWithWorkingHoursAndDurationMoreThanMin(period2));

    assertEquals(expectedMessage, e1.getMessage());
    assertEquals(expectedMessage, e2.getMessage());
  }

  @Test
  void checkTimePeriodWithWorkingHoursAndDurationMoreThanMinIfPeriodDurationLessThanMin() {
    LocalTime startTime = LocalTime.of(8, 0);
    LocalTime endTime = LocalTime.of(9, 0);
    TimePeriod period = new TimePeriod(startTime, endTime);

    String expectedMessage = "Slot has to be 1,5 hours or more";

    Exception e = assertThrows(InvalidTimePeriodBoundaries.class,
        () -> TimePeriodValidator.checkTimePeriodWithWorkingHoursAndDurationMoreThanMin(period));

    assertEquals(expectedMessage, e.getMessage());
  }

  @Test
  void checkTimePeriodWithWorkingHoursAndDurationMoreThanMinWorksProperly() {
    LocalTime startTime1 = LocalTime.of(8, 0);
    LocalTime endTime1 = LocalTime.of(9, 30);
    TimePeriod period1 = new TimePeriod(startTime1, endTime1);

    LocalTime startTime2 = LocalTime.of(20, 0);
    LocalTime endTime2 = LocalTime.of(22, 0);
    TimePeriod period2 = new TimePeriod(startTime2, endTime2);

    try {
      TimePeriodValidator.checkTimePeriodWithWorkingHoursAndDurationMoreThanMin(period1);
      TimePeriodValidator.checkTimePeriodWithWorkingHoursAndDurationMoreThanMin(period2);
    } catch (Exception e) {
      fail("This method should not throw an exception on given input");
    }
  }

  @Test
  void checkTimePeriodWithoutWorkingHoursIfEndTimeBeforeStartTime() {
    LocalTime startTime1 = LocalTime.of(13, 0);
    LocalTime endTime1 = LocalTime.of(11, 30);
    TimePeriod period1 = new TimePeriod(startTime1, endTime1);

    LocalTime startTime2 = LocalTime.of(23, 2);
    LocalTime endTime2 = LocalTime.of(23, 0);
    TimePeriod period2 = new TimePeriod(startTime2, endTime2);

    String expectedMessage1
        = "Start time : " + startTime1 + " is later then end time :  " + endTime1;

    String expectedMessage2
        = "Start time : " + startTime2 + " is later then end time :  " + endTime2;

    Exception e1 = assertThrows(InvalidTimePeriodBoundaries.class,
        () -> TimePeriodValidator.checkTimePeriodWithoutWorkingHours(period1));

    Exception e2 = assertThrows(InvalidTimePeriodBoundaries.class,
        () -> TimePeriodValidator.checkTimePeriodWithoutWorkingHours(period2));

    assertEquals(expectedMessage1, e1.getMessage());
    assertEquals(expectedMessage2, e2.getMessage());
  }

  @Test
  void checkTimePeriodWithoutWorkingHoursIfTimeNotRound() {
    LocalTime startTime1 = LocalTime.of(12, 0);
    LocalTime endTime1 = LocalTime.of(13, 35);
    TimePeriod period1 = new TimePeriod(startTime1, endTime1);

    LocalTime startTime2 = LocalTime.of(17, 5);
    LocalTime endTime2 = LocalTime.of(19, 0);
    TimePeriod period2 = new TimePeriod(startTime2, endTime2);

    String expectedMessage = "Time period has to be rounded to 30 minutes";

    Exception e1 = assertThrows(InvalidTimePeriodBoundaries.class,
        () -> TimePeriodValidator.checkTimePeriodWithoutWorkingHours(period1));

    Exception e2 = assertThrows(InvalidTimePeriodBoundaries.class,
        () -> TimePeriodValidator.checkTimePeriodWithoutWorkingHours(period2));

    assertEquals(expectedMessage, e1.getMessage());
    assertEquals(expectedMessage, e2.getMessage());
  }

  @Test
  void checkTimePeriodWithoutWorkingHoursIfDurationIsNotEqualToNinetyMinutes() {
    LocalTime startTime = LocalTime.of(12, 0);
    LocalTime endTime = LocalTime.of(14, 0);
    TimePeriod period = new TimePeriod(startTime, endTime);

    String expectedMessage = "Booking time has to be 1,5 hours";

    Exception e = assertThrows(InvalidBookingDurationException.class,
        () -> TimePeriodValidator.checkTimePeriodWithoutWorkingHours(period));

    assertEquals(expectedMessage, e.getMessage());
  }

  @Test
  void checkTimePeriodWithoutWorkingHoursWorksProperly() {
    LocalTime startTime = LocalTime.of(12, 0);
    LocalTime endTime = LocalTime.of(13, 30);
    TimePeriod period = new TimePeriod(startTime, endTime);

    try {
      TimePeriodValidator.checkTimePeriodWithoutWorkingHours(period);
    } catch (Exception e) {
      fail("This method should not throw an exception on given input");
    }
  }

  @Test
  void checkTimePeriodWithoutWorkingHoursAndDurationMoreThanMinIfEndTimeBeforeStartTime() {
    LocalTime startTime1 = LocalTime.of(13, 0);
    LocalTime endTime1 = LocalTime.of(11, 0);
    TimePeriod period1 = new TimePeriod(startTime1, endTime1);

    LocalTime startTime2 = LocalTime.of(23, 2);
    LocalTime endTime2 = LocalTime.of(23, 0);
    TimePeriod period2 = new TimePeriod(startTime2, endTime2);

    String expectedMessage1
        = "Start time : " + startTime1 + " is later then end time :  " + endTime1;

    String expectedMessage2
        = "Start time : " + startTime2 + " is later then end time :  " + endTime2;

    Exception e1 = assertThrows(InvalidTimePeriodBoundaries.class,
        () -> TimePeriodValidator.checkTimePeriodWithoutWorkingHoursAndDurationMoreThanMin(period1)
    );

    Exception e2 = assertThrows(InvalidTimePeriodBoundaries.class,
        () -> TimePeriodValidator.checkTimePeriodWithoutWorkingHoursAndDurationMoreThanMin(period2)
    );

    assertEquals(expectedMessage1, e1.getMessage());
    assertEquals(expectedMessage2, e2.getMessage());
  }

  @Test
  void checkTimePeriodWithoutWorkingHoursAndDurationMoreThanMinIfTimeNotRound() {
    LocalTime startTime1 = LocalTime.of(12, 0);
    LocalTime endTime1 = LocalTime.of(13, 35);
    TimePeriod period1 = new TimePeriod(startTime1, endTime1);

    LocalTime startTime2 = LocalTime.of(17, 5);
    LocalTime endTime2 = LocalTime.of(19, 0);
    TimePeriod period2 = new TimePeriod(startTime2, endTime2);

    String expectedMessage = "Time period has to be rounded to 30 minutes";

    Exception e1 = assertThrows(InvalidTimePeriodBoundaries.class,
        () -> TimePeriodValidator.checkTimePeriodWithoutWorkingHoursAndDurationMoreThanMin(period1)
    );

    Exception e2 = assertThrows(InvalidTimePeriodBoundaries.class,
        () -> TimePeriodValidator.checkTimePeriodWithoutWorkingHoursAndDurationMoreThanMin(period2)
    );

    assertEquals(expectedMessage, e1.getMessage());
    assertEquals(expectedMessage, e2.getMessage());
  }

  @Test
  void checkTimePeriodWithoutWorkingHoursAndDurationMoreThanMinIfPeriodDurationLessThanMin() {
    LocalTime startTime = LocalTime.of(8, 0);
    LocalTime endTime = LocalTime.of(9, 0);
    TimePeriod period = new TimePeriod(startTime, endTime);

    String expectedMessage = "Slot has to be 1,5 hours or more";

    Exception e = assertThrows(InvalidTimePeriodBoundaries.class,
        () -> TimePeriodValidator.checkTimePeriodWithoutWorkingHoursAndDurationMoreThanMin(period)
    );

    assertEquals(expectedMessage, e.getMessage());
  }

  @Test
  void checkTimePeriodWithoutWorkingHoursAndDurationMoreThanMinWorksProperly() {
    LocalTime startTime = LocalTime.of(12, 0);
    LocalTime endTime = LocalTime.of(14, 30);
    TimePeriod period = new TimePeriod(startTime, endTime);

    try {
      TimePeriodValidator.checkTimePeriodWithoutWorkingHoursAndDurationMoreThanMin(period);
    } catch (Exception e) {
      fail("This method should not throw an exception on given input");
    }
  }
}