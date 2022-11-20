package com.intellias.intellistart.interviewplanning.controller;

import com.intellias.intellistart.interviewplanning.controller.dto.UserDto;
import com.intellias.intellistart.interviewplanning.controller.dto.WeekDto;
import com.intellias.intellistart.interviewplanning.security.JwtUserDetails;
import com.intellias.intellistart.interviewplanning.util.DateUtils;
import org.springframework.security.core.context.SecurityContextHolder;
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

  /**
   * Returns data about logged-in user.
   *
   * @return UserDto with data about logged-in user
   */
  @GetMapping("/me")
  public UserDto getMe() {
    JwtUserDetails details = (JwtUserDetails) SecurityContextHolder.getContext()
        .getAuthentication().getPrincipal();

    return new UserDto(details);
  }
}