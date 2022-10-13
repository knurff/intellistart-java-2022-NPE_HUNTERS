package com.intellias.intellistart.interviewplanning.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalTime;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * InterviewerSlot dto.
 */
@Data
@NoArgsConstructor
public class InterviewerSlotDto {

  private Long id;

  private int weekNum;

  private String dayOfWeek;

  @JsonFormat(pattern = "HH:mm")
  private LocalTime from;

  @JsonFormat(pattern = "HH:mm")
  private LocalTime to;
}
