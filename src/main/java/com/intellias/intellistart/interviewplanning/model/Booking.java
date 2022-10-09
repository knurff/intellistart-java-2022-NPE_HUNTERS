package com.intellias.intellistart.interviewplanning.model;

import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.Hibernate;

/**
 * Booking entity.
 */
@Entity
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@NoArgsConstructor
@Table(name = "bookings")
public class Booking {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

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

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) {
      return false;
    }
    Booking booking = (Booking) o;
    return id != null && Objects.equals(id, booking.id);
  }

  @Override
  public int hashCode() {
    return getClass().hashCode();
  }
}
