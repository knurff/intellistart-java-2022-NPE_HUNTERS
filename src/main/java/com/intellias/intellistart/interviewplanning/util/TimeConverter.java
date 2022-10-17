package com.intellias.intellistart.interviewplanning.util;

import java.time.LocalTime;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * Util class with methods that converts LocalTime.
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class TimeConverter {

  public static int convertTimeToMinutes(LocalTime localTime) {
    return localTime.getHour() * 60 + localTime.getMinute();
  }
}
