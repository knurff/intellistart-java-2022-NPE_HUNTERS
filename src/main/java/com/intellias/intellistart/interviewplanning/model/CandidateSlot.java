package com.intellias.intellistart.interviewplanning.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.time.LocalDate;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

/**
 * CandidateSlot entity.
 */

@Entity
@Data
@NoArgsConstructor
@Table(name = "candidate_slots")
public class CandidateSlot {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "candidate_email")
  @NonNull
  private String email;

  @NonNull
  private LocalDate date;

  @Embedded
  @NonNull
  private TimePeriod period;

  @OneToMany(fetch = FetchType.LAZY, mappedBy = "candidateSlot")
  @JsonIgnore
  private Set<Booking> bookings;

}
