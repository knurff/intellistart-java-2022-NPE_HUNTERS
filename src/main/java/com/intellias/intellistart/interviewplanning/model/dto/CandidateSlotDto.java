package com.intellias.intellistart.interviewplanning.model.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.intellias.intellistart.interviewplanning.model.Booking;
import com.intellias.intellistart.interviewplanning.model.TimePeriod;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

/**
 * CandidateSlot dto.
 */
@Getter
@Setter
@EqualsAndHashCode(of = {"email", "date", "period"})
@RequiredArgsConstructor
public class CandidateSlotDto {

  @JsonInclude(value = Include.NON_NULL)
  private Long id;

  private String email;

  private LocalDate date;

  private TimePeriod period;

  @JsonInclude(value = Include.NON_EMPTY)
  private Set<Booking> bookings;
}
