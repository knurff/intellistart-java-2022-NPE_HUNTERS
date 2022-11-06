package com.intellias.intellistart.interviewplanning.controller;

import com.intellias.intellistart.interviewplanning.controller.response.GetWeekResponse;
import com.intellias.intellistart.interviewplanning.util.DateUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * UserController controller.
 */
@RestController
public class UserController {

  @GetMapping("weeks/current")
  public GetWeekResponse getCurrentWeek() {
    return new GetWeekResponse(DateUtils.getCurrentWeek());
  }

  @GetMapping("weeks/next")
  public GetWeekResponse getNextWeek() {
    return new GetWeekResponse(DateUtils.getNextWeek());
  }
}