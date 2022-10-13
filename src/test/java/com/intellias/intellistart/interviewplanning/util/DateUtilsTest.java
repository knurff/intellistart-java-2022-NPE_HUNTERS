package com.intellias.intellistart.interviewplanning.util;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
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
    final int expected = 1;

    final int actual = DateUtils.getWeekFrom(from, current);

    assertEquals(expected, actual);
  }

  @Test
  void testGetWeekFromWhenFromAndCurrentAreOnTheSameWeek() {
    final LocalDate from = LocalDate.of(2022, 1, 4);
    final LocalDate current = LocalDate.of(2022, 1, 9);
    final int expected = 1;

    final int actual = DateUtils.getWeekFrom(from, current);

    assertEquals(expected, actual);
  }

  @Test
  void testGetWeekFromWhenFromDayIsLessThanCurrentDay() {
    final LocalDate from = LocalDate.of(2022, 1, 1);
    final LocalDate current = LocalDate.of(2022, 1, 6);
    final int expected = 2;

    final int actual = DateUtils.getWeekFrom(from, current);

    assertEquals(expected, actual);
  }

  @Test
  void testGetWeekFromWhenFromDayIsGreaterThanCurrentDay() {
    final LocalDate from = LocalDate.of(2022, 1, 1);
    final LocalDate current = LocalDate.of(2022, 1, 9);
    final int expected = 2;

    final int actual = DateUtils.getWeekFrom(from, current);

    assertEquals(expected, actual);
  }

  @Test
  void testGetWeekFromOnOneOfOctoberDate() {
    final LocalDate from = LocalDate.of(2022, 1, 1);
    final LocalDate current = LocalDate.of(2022, 10, 13);
    final int expected = 42;

    final int actual = DateUtils.getWeekFrom(from, current);

    assertEquals(expected, actual);
  }

  @Test
  void testGetWeekFromWhenDatesAreInDifferentYears() {
    final LocalDate from = LocalDate.of(2022, 1, 1);
    final LocalDate current = LocalDate.of(2023, 10, 13);
    final int expected = 94;

    final int actual = DateUtils.getWeekFrom(from, current);

    assertEquals(expected, actual);
  }

  @Test
  void testGetNextWeekFromOnOneOfOctoberDate() {
    final LocalDate from = LocalDate.of(2022, 1, 1);
    final LocalDate current = LocalDate.of(2022, 10, 13);
    final int expected = 43;

    final int actual = DateUtils.getNextWeekFrom(from, current);

    assertEquals(expected, actual);
  }
}