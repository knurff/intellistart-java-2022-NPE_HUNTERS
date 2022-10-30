package com.intellias.intellistart.interviewplanning.controller.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import java.time.LocalTime;
import lombok.Builder;
import lombok.Data;

/**
 * Booking dto.
 */
@Data
@Builder
public class BookingDto {

  @JsonInclude(value = Include.NON_NULL)
  private Long id;

  private Long interviewerSlotId;

  private Long candidateSlotId;

  @JsonFormat(pattern = "HH:mm")
  private LocalTime from;

  @JsonFormat(pattern = "HH:mm")
  private LocalTime to;

  private String subject;

  private String description;
}
