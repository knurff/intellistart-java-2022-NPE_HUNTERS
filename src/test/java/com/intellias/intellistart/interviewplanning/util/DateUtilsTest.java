package com.intellias.intellistart.interviewplanning.util;

import static org.junit.jupiter.api.Assertions.*;

import java.time.DayOfWeek;
import java.time.LocalDate;
import net.bytebuddy.asm.Advice.Local;
import org.junit.jupiter.api.Test;

/**
 * Unit tests for DateUtils class.
 */
class DateUtilsTest {

  @Test
  void testGetWeekFromWhenFromGreaterThanCurrent() {
    final LocalDate from = LocalDate.of(2022, 1, 1);
    final LocalDate current = LocalDate.of(2021, 12, 31);
    final String expectedMessage = "'from' value must be less than current";

    IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> DateUtils.getWeekFrom(from, current));

    assertEquals(expectedMessage, exception.getMessage());
  }

  @Test
  void testGetWeekFromWhenFromIsEqualToCurrent() {
    final LocalDate from = LocalDate.of(2022, 1, 1);
    final LocalDate current = LocalDate.of(2022, 1, 1);
    final int expected = 0;

    final int actual = DateUtils.getWeekFrom(from, current);

    assertEquals(expected, actual);
  }

  @Test
  void testGetWeekFromWhenFromAndCurrentAreOnTheSameWeek() {
    final LocalDate from = LocalDate.of(2022, 1, 4);
    final LocalDate current = LocalDate.of(2022, 1, 9);
    final int expected = 0;

    final int actual = DateUtils.getWeekFrom(from, current);

    assertEquals(expected, actual);
  }

  @Test
  void testGetWeekFromWhenFromDayIsLessThanCurrentDay() {
    final LocalDate from = LocalDate.of(2022, 1, 1);
    final LocalDate current = LocalDate.of(2022, 1, 6);
    final int expected = 1;

    final int actual = DateUtils.getWeekFrom(from, current);

    assertEquals(expected, actual);
  }

  @Test
  void testGetWeekFromWhenFromDayIsGreaterThanCurrentDay() {
    final LocalDate from = LocalDate.of(2022, 1, 1);
    final LocalDate current = LocalDate.of(2022, 1, 9);
    final int expected = 1;

    final int actual = DateUtils.getWeekFrom(from, current);

    assertEquals(expected, actual);
  }

  @Test
  void testGetWeekFromOnOneOfOctoberDate() {
    final LocalDate from = LocalDate.of(2022, 1, 1);
    final LocalDate current = LocalDate.of(2022, 10, 13);
    final int expected = 41;

    final int actual = DateUtils.getWeekFrom(from, current);

    assertEquals(expected, actual);
  }

  @Test
  void testGetWeekFromWhenDatesAreInDifferentYears() {
    final LocalDate from = LocalDate.of(2022, 1, 1);
    final LocalDate current = LocalDate.of(2023, 10, 13);
    final int expected = 93;

    final int actual = DateUtils.getWeekFrom(from, current);

    assertEquals(expected, actual);
  }

  @Test
  void testGetNextWeekFromOnOneOfOctoberDate() {
    final LocalDate from = LocalDate.of(2022, 1, 1);
    final LocalDate current = LocalDate.of(2022, 10, 13);
    final int expected = 42;

    final int actual = DateUtils.getNextWeekFrom(from, current);

    assertEquals(expected, actual);
  }

  @Test
  void testGetDateOfDayOfWeek() {
    final LocalDate expectedDate = LocalDate.of(2022, 1, 1);
    final int weekNumber = 0;
    final DayOfWeek dayOfWeek = expectedDate.getDayOfWeek();

    final LocalDate actualDate = DateUtils.getDateOfDayOfWeek(weekNumber, dayOfWeek);

    assertNotNull(actualDate);
    assertEquals(expectedDate, actualDate);
  }

  @Test
  void testCompatibilityBetweenGetDateOfDayOfWeekAndGetWeekFrom() {
    final LocalDate expectedDate = LocalDate.now();
    final int weekNumber = DateUtils.getWeekFrom(DateUtils.DEFAULT_NUMBERING_FROM, expectedDate);
    final DayOfWeek dayOfWeek = expectedDate.getDayOfWeek();

    final LocalDate actualDate = DateUtils.getDateOfDayOfWeek(weekNumber, dayOfWeek);

    assertNotNull(actualDate);
    assertEquals(expectedDate, actualDate);

    assertEquals(weekNumber, 42);
  }
}