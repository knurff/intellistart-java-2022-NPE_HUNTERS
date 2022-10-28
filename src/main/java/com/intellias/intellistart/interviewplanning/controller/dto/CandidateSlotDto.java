package com.intellias.intellistart.interviewplanning.controller.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

/**
 * CandidateSlot dto.
 */
@Builder
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(exclude = {"id","bookings"})
@RequiredArgsConstructor
public class CandidateSlotDto {

  @JsonInclude(value = Include.NON_NULL)
  private Long id;

  private String email;

  private LocalDate date;

  @JsonFormat(pattern = "HH:mm")
  private LocalTime from;

  @JsonFormat(pattern = "HH:mm")
  private LocalTime to;

  @JsonInclude(value = Include.NON_EMPTY)
  private Set<BookingDto> bookings;
}
