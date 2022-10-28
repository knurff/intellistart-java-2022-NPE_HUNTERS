package com.intellias.intellistart.interviewplanning.controller.dto.mapper;

import com.intellias.intellistart.interviewplanning.controller.dto.CandidateSlotDto;
import com.intellias.intellistart.interviewplanning.model.CandidateSlot;
import com.intellias.intellistart.interviewplanning.model.TimePeriod;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * CandidateSlotsDtoMapper class for mapping interviewer slots DTOs from model entities and vice
 * versa.
 */
@Component
@RequiredArgsConstructor
public class CandidateSlotMapper {

  private final BookingMapper bookingMapper;

  /**
   * Returns CandidateSlotDto from entity object.
   *
   * @param entity interviewer slot entity
   * @return created dto
   */
  public CandidateSlotDto mapToCandidateSlotDto(CandidateSlot entity) {
    return CandidateSlotDto.builder()
        .id(entity.getId())
        .email(entity.getEmail())
        .date(entity.getDate())
        .from(entity.getPeriod().getStartTime())
        .to(entity.getPeriod().getEndTime())
        .bookings(bookingMapper.createBookingDtoSet(entity.getBookings()))
        .build();
  }

  /**
   * Returns CandidateSlotDto from entity object.
   *
   * @param entities list of interviewer slot entities
   * @return list of CandidateSlotDto
   */
  public List<CandidateSlotDto> mapToCandidateSlotDtoList(
      List<CandidateSlot> entities) {
    return entities.stream()
        .map(this::mapToCandidateSlotDto)
        .collect(Collectors.toList());
  }

  /**
   * Returns converted CandidateSlot from CandidateSlotDto.
   *
   * @param dto CandidateSlotDto
   * @return created entity
   */
  public CandidateSlot mapToCandidateSlotEntity(CandidateSlotDto dto) {
    return CandidateSlot.builder()
        .email(dto.getEmail())
        .date(dto.getDate())
        .period(new TimePeriod(dto.getFrom(), dto.getTo()))
        .build();
  }

}