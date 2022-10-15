package com.intellias.intellistart.interviewplanning.controller.dto;

import com.intellias.intellistart.interviewplanning.model.TimePeriod;
import lombok.Builder;
import lombok.Data;

/**
 * Booking dto.
 */

@Data
@Builder
public class BookingDto {

  private Long id;

  private TimePeriod period;

  private String subject;

  private String description;
}
