package com.intellias.intellistart.interviewplanning.model;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable
@Data
@AllArgsConstructor
@NoArgsConstructor
public class WeekBooking implements Serializable {

  @Column(nullable = true)
  private int currentWeek = -1;

  @Column(nullable = true)
  private int nextWeek = -1;
}
