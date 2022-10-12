package com.intellias.intellistart.interviewplanning.controller;

import com.intellias.intellistart.interviewplanning.util.DateUtils;
import java.time.LocalDate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * UserController controller.
 */
@RestController
public class UserController {

  @GetMapping("weeks/current")
  public int getCurrentWeek() {
    return DateUtils.getWeekFrom(DateUtils.NUMBERING_FROM, LocalDate.now());
  }
}