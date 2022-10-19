package com.intellias.intellistart.interviewplanning.util;

import com.intellias.intellistart.interviewplanning.controller.dto.CandidateSlotDto;
import com.intellias.intellistart.interviewplanning.model.CandidateSlot;
import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * Util class, which contains methods for converting CandidateSlotDto to CandidateSlot entity and
 * CandidateSlot entity to CandidateSlotDto.
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CandidateSlotMapper {

  /**
   * Returns converted CandidateSlot from CandidateSlotDto.
   */
  public static CandidateSlot mapDtoToEntity(CandidateSlotDto candidateSlotDto) {
    CandidateSlot candidateSlot = new CandidateSlot();

    candidateSlot.setEmail(candidateSlotDto.getEmail());
    candidateSlot.setDate(candidateSlotDto.getDate());
    candidateSlot.setPeriod(candidateSlotDto.getPeriod());

    return candidateSlot;
  }

  /**
   * Returns converted CandidateSlotDto list from CandidateSlot list.
   */
  public static List<CandidateSlotDto> convertCandidateSlotListToDtoList(
      List<CandidateSlot> candidateSlots) {
    List<CandidateSlotDto> candidateSlotDtoList = new ArrayList<>();

    for (CandidateSlot candidateSlot : candidateSlots) {
      candidateSlotDtoList.add(mapEntityToDto(candidateSlot));
    }

    return candidateSlotDtoList;
  }

  /**
   * Returns converted CandidateSlotDto from CandidateSlot.
   */
  public static CandidateSlotDto mapEntityToDto(CandidateSlot candidateSlot) {
    CandidateSlotDto candidateSlotDto = new CandidateSlotDto();

    candidateSlotDto.setId(candidateSlot.getId());
    candidateSlotDto.setEmail(candidateSlot.getEmail());
    candidateSlotDto.setDate(candidateSlot.getDate());
    candidateSlotDto.setPeriod(candidateSlot.getPeriod());
    candidateSlotDto.setBookings(candidateSlot.getBookings());

    return candidateSlotDto;
  }

}
