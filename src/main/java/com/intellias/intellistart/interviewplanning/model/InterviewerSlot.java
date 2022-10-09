package com.intellias.intellistart.interviewplanning.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.Set;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.NonNull;

/**
 * InterviewerSlot entity.
 */

@Entity
@Data
@NoArgsConstructor
@Table(name = "interviewer_slots")
public class InterviewerSlot {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private int week;

  @NonNull
  private String dayOfWeek;

  @Embedded
  @NonNull
  private TimePeriod period;

  @ManyToOne
  @JoinColumn(name = "interviewer_id")
  private User interviewerId;


  @OneToMany(fetch = FetchType.LAZY, mappedBy = "interviewerSlot")
  @JsonIgnore
  private Set<Booking> bookings;
}
