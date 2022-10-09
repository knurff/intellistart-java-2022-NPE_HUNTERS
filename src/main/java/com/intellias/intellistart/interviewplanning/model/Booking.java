package com.intellias.intellistart.interviewplanning.model;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

/**
 * Booking entity.
 */
@Entity
@Data
@NoArgsConstructor
@Table(name = "bookings")
public class Booking {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;

  @Embedded
  @NonNull
  private TimePeriod period;

  @ManyToOne
  @JoinColumn(name = "interviewer_slot_id")
  private InterviewerSlot interviewerSlot;

  @ManyToOne
  @JoinColumn(name = "candidate_slot_id")
  private CandidateSlot candidateSlot;

  private String subject;

  @Column(length = 4000)
  private String description;
}
