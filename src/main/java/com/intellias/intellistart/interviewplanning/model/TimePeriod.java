package com.intellias.intellistart.interviewplanning.model;

import java.io.Serializable;
import java.time.LocalTime;
import javax.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * TimePeriod structure.
 */

@Embeddable
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TimePeriod implements Serializable {

  private LocalTime startTime;

  private LocalTime endTime;
}
