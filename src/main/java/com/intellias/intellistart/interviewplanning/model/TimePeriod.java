package com.intellias.intellistart.interviewplanning.model;

import java.io.Serializable;
import java.time.LocalTime;
import javax.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * TimePeriod structure.
 */

@Embeddable
@Data
@AllArgsConstructor
public class TimePeriod implements Serializable {

  private LocalTime startTime;

  private LocalTime endTime;
}
