package com.intellias.intellistart.interviewplanning.controller.dto.creator;

import com.intellias.intellistart.interviewplanning.controller.dto.InterviewerSlotDto;
import com.intellias.intellistart.interviewplanning.model.InterviewerSlot;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * InterviewerSlotsDtoCreator class for creating interviewer slots DTOs from model entities.
 */
@Component
@RequiredArgsConstructor
public class InterviewerSlotsDtoCreator {

  private final BookingDtoCreator bookingDtoCreator;

  /**
   * Returns interviewerSlotDto from entity object.
   *
   * @param interviewerSlot interviewer slot entity
   * @return InterviewerSlotDto
   */

  public InterviewerSlotDto createInterviewerSlotsDto(InterviewerSlot interviewerSlot) {
    return InterviewerSlotDto.builder()
        .id(interviewerSlot.getId())
        .weekNum(interviewerSlot.getWeek())
        .dayOfWeek(interviewerSlot.getDayOfWeek().toString())
        .from(interviewerSlot.getPeriod().getStartTime())
        .to(interviewerSlot.getPeriod().getEndTime())
        .bookings(bookingDtoCreator.createBookingDtoSet(interviewerSlot.getBookings()))
        .build();
  }

  /**
   * Returns interviewerSlotDto from entity object.
   *
   * @param interviewerSlotList list of interviewer slot entities
   * @return list of InterviewerSlotDto
   */

  public List<InterviewerSlotDto> createInterviewerSlotsDtoList(
      List<InterviewerSlot> interviewerSlotList) {
    return interviewerSlotList.stream()
        .map(this::createInterviewerSlotsDto)
        .collect(Collectors.toList());
  }

}
