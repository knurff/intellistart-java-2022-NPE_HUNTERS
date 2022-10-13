package com.intellias.intellistart.interviewplanning.controller;

import com.intellias.intellistart.interviewplanning.util.DateUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * UserController controller.
 */
@RestController
public class UserController {

  @GetMapping("weeks/current")
  public int getCurrentWeek() {
    return DateUtils.getCurrentWeek();
  }

  @GetMapping("weeks/next")
  public int getNextWeek() {
    return DateUtils.getNextWeek();
  }
}