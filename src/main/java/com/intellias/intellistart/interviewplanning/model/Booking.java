package com.intellias.intellistart.interviewplanning.model;

import java.util.Set;

/**
 * Booking entity.
 */
public class Booking {
  private Long id;
  private Set<Slot> candidateId;
  private Set<Slot> interviewerId;
}
