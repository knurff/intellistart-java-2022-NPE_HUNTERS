package com.intellias.intellistart.interviewplanning.model;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.WeekFields;
import java.util.Objects;
import java.util.Set;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.ToString.Exclude;
import org.hibernate.Hibernate;
import org.springframework.lang.NonNull;

/**
 * InterviewerSlot entity.
 */

@Entity
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@NoArgsConstructor
@Table(name = "interviewer_slots")
public class InterviewerSlot {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private int week;

  @NonNull
  @Enumerated(EnumType.STRING)
  private DayOfWeek dayOfWeek;

  @Embedded
  @NonNull
  private TimePeriod period;

  @ManyToOne
  @JoinColumn(name = "interviewer_id")
  private User interviewerId;


  @OneToMany(fetch = FetchType.LAZY, mappedBy = "interviewerSlot")
  @Exclude
  private Set<Booking> bookings;

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) {
      return false;
    }
    InterviewerSlot that = (InterviewerSlot) o;
    return id != null && Objects.equals(id, that.id);
  }

  @Override
  public int hashCode() {
    return getClass().hashCode();
  }

  /**
   * Returns date relative to year, {@code week} and {@code dayOfWeek}.
   *
   * @return the LocalDate
   */
  public LocalDate getDate() {
    return LocalDate.now().with(WeekFields.ISO.weekBasedYear(), LocalDate.now().getYear())
        .with(WeekFields.ISO.weekOfYear(), week)
        .with(WeekFields.ISO.dayOfWeek(), dayOfWeek.getValue());
  }
}
