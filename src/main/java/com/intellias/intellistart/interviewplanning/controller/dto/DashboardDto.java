package com.intellias.intellistart.interviewplanning.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * A dto representing all bookings, coordinator and candidate slots for a particular week.
 */
@Data
@AllArgsConstructor
public class DashboardDto {
  private final DashboardDayDto monday;
  private final DashboardDayDto tuesday;
  private final DashboardDayDto wednesday;
  private final DashboardDayDto thursday;
  private final DashboardDayDto friday;
}
