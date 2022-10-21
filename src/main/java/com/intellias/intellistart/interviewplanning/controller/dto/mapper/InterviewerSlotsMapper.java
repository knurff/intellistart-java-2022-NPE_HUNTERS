package com.intellias.intellistart.interviewplanning.controller.dto.mapper;

import com.intellias.intellistart.interviewplanning.controller.dto.InterviewerSlotDto;
import com.intellias.intellistart.interviewplanning.model.InterviewerSlot;
import com.intellias.intellistart.interviewplanning.model.TimePeriod;
import com.intellias.intellistart.interviewplanning.model.dayofweek.DayOfWeek;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * InterviewerSlotsDtoMapper class for mapping interviewer slots DTOs from model entities and vice
 * versa.
 */
@Component
@RequiredArgsConstructor
public class InterviewerSlotsMapper {

  private final BookingMapper bookingMapper;

  /**
   * Returns InterviewerSlotDto from entity object.
   *
   * @param entity interviewer slot entity
   * @return created dto
   */

  public InterviewerSlotDto mapToInterviewerSlotsDto(InterviewerSlot entity) {
    return InterviewerSlotDto.builder()
        .id(entity.getId())
        .weekNum(entity.getWeek())
        .dayOfWeek(entity.getDayOfWeek().getDay())
        .from(entity.getPeriod().getStartTime())
        .to(entity.getPeriod().getEndTime())
        .bookings(bookingMapper.createBookingDtoSet(entity.getBookings()))
        .build();
  }

  /**
   * Returns InterviewerSlotDto from entity object.
   *
   * @param entities list of interviewer slot entities
   * @return list of InterviewerSlotDto
   */

  public List<InterviewerSlotDto> mapToInterviewerSlotsDtoList(
      List<InterviewerSlot> entities) {
    return entities.stream()
        .map(this::mapToInterviewerSlotsDto)
        .collect(Collectors.toList());
  }

  /**
   * Returns converted InterviewerSlot from InterviewerSlotDto.
   *
   * @param dto InterviewerSlotDto
   * @return created entity
   */
  public InterviewerSlot mapToInterviewerSlotEntity(InterviewerSlotDto dto) {
    return InterviewerSlot.builder()
        .week(dto.getWeekNum())
        .dayOfWeek(DayOfWeek.fromString(dto.getDayOfWeek()))
        .period(new TimePeriod(dto.getFrom(), dto.getTo()))
        .build();
  }
}
