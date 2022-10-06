package com.intellias.intellistart.interviewplanning.model;

import java.util.Set;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import lombok.Data;

/**
 * Booking entity.
 */
@Entity
@Data
public class Booking {
  @Id @GeneratedValue
  private Long id;
  //private Set<Slot> candidateId;
  //private Set<Slot> interviewerId;
}
