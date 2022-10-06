package com.intellias.intellistart.interviewplanning.model;

import java.util.Set;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * Booking entity.
 */
public class Booking {
  @GeneratedValue
  private Long id;
  private Set<Slot> candidateId;
  private Set<Slot> interviewerId;
}
