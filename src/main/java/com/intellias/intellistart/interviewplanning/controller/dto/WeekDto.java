package com.intellias.intellistart.interviewplanning.controller.dto;

import com.intellias.intellistart.interviewplanning.controller.UserController;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Class, which contains response information for
 * {@link UserController#getCurrentWeek()} and
 * {@link UserController#getNextWeek()}.
 */
@Getter
@AllArgsConstructor
public class WeekDto {
  private final int weekNum;
}
