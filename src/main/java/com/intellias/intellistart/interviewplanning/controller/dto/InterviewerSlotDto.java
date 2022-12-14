package com.intellias.intellistart.interviewplanning.controller.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import java.time.LocalTime;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

/**
 * InterviewerSlot dto.
 */
@Getter
@Builder
@AllArgsConstructor
public class InterviewerSlotDto {

  @JsonInclude(value = Include.NON_NULL)
  private Long id;

  private int weekNum;

  private String dayOfWeek;

  @JsonFormat(pattern = "HH:mm")
  private LocalTime from;

  @JsonFormat(pattern = "HH:mm")
  private LocalTime to;

  @JsonInclude(Include.NON_EMPTY)
  private Set<BookingDto> bookings;
}
