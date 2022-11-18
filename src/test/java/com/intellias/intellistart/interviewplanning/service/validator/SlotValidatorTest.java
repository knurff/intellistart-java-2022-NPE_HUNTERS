package com.intellias.intellistart.interviewplanning.service.validator;

import static org.junit.jupiter.api.Assertions.*;

import com.intellias.intellistart.interviewplanning.exception.InvalidSlotDateException;
import com.intellias.intellistart.interviewplanning.exception.InvalidTimePeriodBoundaries;
import com.intellias.intellistart.interviewplanning.exception.SlotIsOverlappingException;
import com.intellias.intellistart.interviewplanning.model.CandidateSlot;
import com.intellias.intellistart.interviewplanning.model.InterviewerSlot;
import com.intellias.intellistart.interviewplanning.service.factory.CandidateSlotFactory;
import com.intellias.intellistart.interviewplanning.service.factory.InterviewerSlotFactory;
import com.intellias.intellistart.interviewplanning.util.DateUtils;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.Test;

class SlotValidatorTest {

  @Test
  void validateCandidateSlotThrowsAnExceptionIfSlotDateIsNotInFuture() {
    Long slotId = 1L;
    CandidateSlot slot = CandidateSlotFactory.createSlotByDateAndTimePeriod(
        LocalDate.now().minusDays(1L),
        LocalTime.of(9, 0),
        LocalTime.of(11, 0)
    );

    String expectedMessage = "Date must be in future";

    Exception e = assertThrows(InvalidSlotDateException.class,
        () -> SlotValidator.validateCandidateSlot(slot, Collections.emptyList(), slotId));

    assertEquals(expectedMessage, e.getMessage());
  }

  @Test
  void validateCandidateSlotThrowsAnExceptionIfTimePeriodIsInPastCondition() {
    Long slotId = 1L;
    CandidateSlot slot = CandidateSlotFactory.createSlotByDateAndTimePeriod(
        LocalDate.now(),
        LocalTime.now().minusMinutes(90),
        LocalTime.now()
    );

    String expectedMessage = "Slot time period must be in future";

    Exception e = assertThrows(InvalidTimePeriodBoundaries.class,
        () -> SlotValidator.validateCandidateSlot(slot, Collections.emptyList(), slotId));

    assertEquals(expectedMessage, e.getMessage());
  }

  @Test
  void validateCandidateSlotThrowsAnExceptionIfOverlappingExists() {
    Long slotId = 1L;
    List<CandidateSlot> anotherSlots = new ArrayList<>();
    anotherSlots.add(CandidateSlotFactory.createSlotByDateAndTimePeriod(
        LocalDate.now().plusDays(1L),
        LocalTime.of(12, 0),
        LocalTime.of(13, 30)
    ));

    CandidateSlot slot = CandidateSlotFactory.createSlotByDateAndTimePeriod(
        LocalDate.now().plusDays(1L),
        LocalTime.of(10, 30),
        LocalTime.of(12, 30)
    );

    String expectedMessage = "This slot is overlapping another";

    Exception e = assertThrows(SlotIsOverlappingException.class,
        () -> SlotValidator.validateCandidateSlot(slot, anotherSlots, slotId));

    assertEquals(expectedMessage, e.getMessage());
  }

  @Test
  void validateCandidateSlotWorksProperly() {
    Long slotId = 1L;
    List<CandidateSlot> anotherSlots = new ArrayList<>();
    anotherSlots.add(CandidateSlotFactory.createSlotByDateAndTimePeriod(
        LocalDate.now().plusDays(1L),
        LocalTime.of(12, 0),
        LocalTime.of(13, 30)
    ));

    anotherSlots.add(CandidateSlotFactory.createSlotByDateAndTimePeriod(
        LocalDate.now().plusDays(1L),
        LocalTime.of(15, 0),
        LocalTime.of(16, 30)
    ));

    CandidateSlot slot = CandidateSlotFactory.createSlotByDateAndTimePeriod(
        LocalDate.now().plusDays(1L),
        LocalTime.of(13, 30),
        LocalTime.of(15, 0)
    );

    try {
      SlotValidator.validateCandidateSlot(slot, anotherSlots, slotId);
    } catch (Exception e) {
      fail("This method should not throw an exception on given input");
    }
  }

  @Test
  void validateInterviewerSlotThrowsAnExceptionIfSlotDateIsNotInFuture() {
    Long slotId = 1L;
    int week = 0;

    InterviewerSlot slot = InterviewerSlotFactory.createSlotByDateAndTimePeriod(
        week,
        DayOfWeek.MONDAY,
        LocalTime.of(8, 0),
        LocalTime.of(10, 0)
    );

    String expectedMessage = "Date must be in future";

    Exception e = assertThrows(InvalidSlotDateException.class,
        () -> SlotValidator.validateInterviewerSlot(slot, Collections.emptyList(), slotId));

    assertEquals(expectedMessage, e.getMessage());
  }

  @Test
  void validateInterviewerSlotThrowsAnExceptionIfOverlappingExists() {
    Long slotId = 1L;
    int week = DateUtils.getNextWeek();
    List<InterviewerSlot> anotherSlots = new ArrayList<>();
    InterviewerSlot slot = InterviewerSlotFactory.createSlotByDateAndTimePeriod(
        week,
        DayOfWeek.MONDAY,
        LocalTime.of(12, 0),
        LocalTime.of(14, 0)
    );

    anotherSlots.add(InterviewerSlotFactory.createSlotByDateAndTimePeriod(
        week,
        DayOfWeek.MONDAY,
        LocalTime.of(13, 0),
        LocalTime.of(15, 0)
    ));

    String expectedMessage = "This slot is overlapping another";

    Exception e = assertThrows(SlotIsOverlappingException.class,
        () -> SlotValidator.validateInterviewerSlot(slot, anotherSlots, slotId));

    assertEquals(expectedMessage, e.getMessage());
  }

  @Test
  void validateInterviewerSlotWorksProperly() {
    Long slotId = 1L;
    int week = DateUtils.getNextWeek();
    List<InterviewerSlot> anotherSlots = new ArrayList<>();
    anotherSlots.add(InterviewerSlotFactory.createSlotByDateAndTimePeriod(
        week,
        DayOfWeek.MONDAY,
        LocalTime.of(12, 0),
        LocalTime.of(13, 30)
    ));

    anotherSlots.add(InterviewerSlotFactory.createSlotByDateAndTimePeriod(
        week,
        DayOfWeek.MONDAY,
        LocalTime.of(15, 0),
        LocalTime.of(16, 30)
    ));

    InterviewerSlot slot = InterviewerSlotFactory.createSlotByDateAndTimePeriod(
        week,
        DayOfWeek.MONDAY,
        LocalTime.of(13, 30),
        LocalTime.of(15, 0)
    );

    try {
      SlotValidator.validateInterviewerSlot(slot, anotherSlots, slotId);
    } catch (Exception e) {
      fail("This method should not throw an exception on given input");
    }
  }
}
