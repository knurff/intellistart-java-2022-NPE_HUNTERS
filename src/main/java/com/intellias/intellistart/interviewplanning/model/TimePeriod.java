package com.intellias.intellistart.interviewplanning.model;

import java.io.Serializable;
import java.time.LocalTime;
import javax.persistence.Embeddable;
import lombok.Data;

/**
 * TimePeriod structure.
 */

@Embeddable
@Data
public class TimePeriod implements Serializable {

  private LocalTime startTime;

  private LocalTime endTime;
}
