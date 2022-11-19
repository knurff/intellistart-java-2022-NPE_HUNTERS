package com.intellias.intellistart.interviewplanning.controller;

import com.intellias.intellistart.interviewplanning.controller.dto.WeekDto;
import com.intellias.intellistart.interviewplanning.util.DateUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * UserController controller.
 */
@RestController
public class UserController {

  @GetMapping("/weeks/current")
  public WeekDto getCurrentWeek() {
    return new WeekDto(DateUtils.getCurrentWeek());
  }

  @GetMapping("/weeks/next")
  public WeekDto getNextWeek() {
    return new WeekDto(DateUtils.getNextWeek());
  }
}