package com.intellias.intellistart.interviewplanning.util;

import com.intellias.intellistart.interviewplanning.exception.InvalidDayOfWeekException;
import com.intellias.intellistart.interviewplanning.model.InterviewerSlot;
import com.intellias.intellistart.interviewplanning.model.TimePeriod;
import com.intellias.intellistart.interviewplanning.model.dayofweek.DayOfWeek;
import com.intellias.intellistart.interviewplanning.model.dto.InterviewerSlotDto;

/**
 * Util class, which contains methods for converting Dto to entity and entity to Dto.
 */
public final class MappingUtils {

  /**
   * Returns converted InterviewerSlotDto from InterviewerSlot.
   *
   * @param entity InterviewerSlot
   * @return created {@code dto}
   */
  public static InterviewerSlotDto mapToInterviewerSlotDto(InterviewerSlot entity) {

    InterviewerSlotDto dto = new InterviewerSlotDto();

    dto.setId(entity.getId());
    dto.setWeekNum(entity.getWeek());
    dto.setDayOfWeek(entity.getDayOfWeek().getDay());
    dto.setFrom(entity.getPeriod().getStartTime());
    dto.setTo(entity.getPeriod().getEndTime());

    return dto;
  }

  /**
   * Returns converted InterviewerSlot from InterviewerSlotDto.
   *
   * @param dto InterviewerSlotDto
   * @return created {@code entity}
   */
  public static InterviewerSlot mapToInterviewerSlotEntity(InterviewerSlotDto dto) {

    InterviewerSlot entity = new InterviewerSlot();

    entity.setWeek(dto.getWeekNum());
    entity.setDayOfWeek(DayOfWeek.fromString(dto.getDayOfWeek()));
    TimePeriod time = new TimePeriod(dto.getFrom(), dto.getTo());
    entity.setPeriod(time);

    return entity;

  }
}
