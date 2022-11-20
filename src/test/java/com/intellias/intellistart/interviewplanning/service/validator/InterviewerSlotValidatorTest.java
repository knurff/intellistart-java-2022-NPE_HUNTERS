package com.intellias.intellistart.interviewplanning.service.validator;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mockStatic;

import com.intellias.intellistart.interviewplanning.exception.InvalidDayForSlotCreationException;
import com.intellias.intellistart.interviewplanning.exception.InvalidSlotDateException;
import com.intellias.intellistart.interviewplanning.model.InterviewerSlot;
import com.intellias.intellistart.interviewplanning.service.factory.InterviewerSlotFactory;
import com.intellias.intellistart.interviewplanning.util.DateUtils;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collections;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

class InterviewerSlotValidatorTest {
  
  private static MockedStatic<LocalDate> localDateMock;
  
  @BeforeAll
  static void setUp() {
    localDateMock = mockStatic(LocalDate.class,
        Mockito.CALLS_REAL_METHODS);
  }

  @AfterAll
  static void closeResources() {
    localDateMock.close();
  }

  @Test
  void validateSlotForNextWeekThrowsAnExceptionIfDateIsNotOnTheNextWeek() {
    Long slotId = 1L;
    InterviewerSlot slot = InterviewerSlotFactory.createSlotByDateAndTimePeriod(
        DateUtils.getNextWeek() + 2,
        DayOfWeek.MONDAY,
        LocalTime.of(10, 0),
        LocalTime.of(11, 30)
    );

    String expectedMessage = "Interviewer can create slots only for next week";

    Exception e = assertThrows(InvalidSlotDateException.class,
        () -> InterviewerSlotValidator.validateSlotForNextWeek(slot,
            Collections.emptyList(), slotId));

    assertEquals(expectedMessage, e.getMessage());
  }

  @Test
  void validateSlotForNextWeekThrowsAnExceptionIfSlotWasCreatedAfterFriday() {
    Long slotId = 1L;
    LocalDate todayDate = LocalDate.of(2022, 11, 19);

    localDateMock.when(LocalDate::now).thenReturn(todayDate);

    InterviewerSlot slot = InterviewerSlotFactory.createSlotByDateAndTimePeriod(
        DateUtils.getNextWeek(),
        DayOfWeek.MONDAY,
        LocalTime.of(10, 0),
        LocalTime.of(11, 30)
    );

    String expectedMessage =
        "Interviewer slot can be created only until end of Friday of current week";

    Exception e = assertThrows(InvalidDayForSlotCreationException.class,
        () -> InterviewerSlotValidator.validateSlotForNextWeek(slot,
            Collections.emptyList(), slotId));

    assertEquals(expectedMessage, e.getMessage());
    localDateMock.clearInvocations();
  }

  @Test
  void validateSlotForNextWeekWorksProperly() {
    Long slotId = 1L;
    LocalDate todayDate = LocalDate.of(2022, 11, 18);

    localDateMock.when(LocalDate::now).thenReturn(todayDate);

    InterviewerSlot slot = InterviewerSlotFactory.createSlotByDateAndTimePeriod(
        DateUtils.getNextWeek(),
        DayOfWeek.MONDAY,
        LocalTime.of(10, 0),
        LocalTime.of(11, 30)
    );

    try {
      InterviewerSlotValidator.validateSlotForNextWeek(slot,
          Collections.emptyList(), slotId);
    } catch (Exception e) {
      fail("This method should not throw an exception on given input");
    } finally {
      localDateMock.clearInvocations();
    }
  }

  @Test
  void validateSlotForCurrentAndNextWeekThrowsAnExceptionIfSlotDateIsNotOnCurrentOrNextWeek() {
    Long slotId = 1L;
    InterviewerSlot slot = InterviewerSlotFactory.createSlotByDateAndTimePeriod(
        DateUtils.getNextWeek() + 2,
        DayOfWeek.MONDAY,
        LocalTime.of(10, 0),
        LocalTime.of(11, 30)
    );

    String expectedMessage = "Coordinator can edit slots only for current or next week";

    Exception e = assertThrows(InvalidSlotDateException.class,
        () -> InterviewerSlotValidator.validateSlotForCurrentAndNextWeek(slot,
            Collections.emptyList(), slotId));

    assertEquals(expectedMessage, e.getMessage());
  }

  @Test
  void validateSlotForCurrentAndNextWeekWorksProperly() {
    Long slotId = 1L;
    InterviewerSlot slot = InterviewerSlotFactory.createSlotByDateAndTimePeriod(
        DateUtils.getNextWeek(),
        DayOfWeek.MONDAY,
        LocalTime.of(10, 0),
        LocalTime.of(11, 30)
    );

    try {
      InterviewerSlotValidator.validateSlotForCurrentAndNextWeek(slot,
          Collections.emptyList(), slotId);
    } catch (Exception e) {
      fail("This method should not throw an exception on given input");
    }
  }
}
