package com.intellias.intellistart.interviewplanning.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.intellias.intellistart.interviewplanning.role.UserRole;
import java.util.Set;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

/**
 * User entity.
 */

@Entity
@Data
@NoArgsConstructor
@Table(name = "users")
public class User {

  @Id
  private String email;

  @NonNull
  @Enumerated(EnumType.STRING)
  private UserRole role;

  @Transient
  private int maxBookingsPerWeek;

  @OneToMany(fetch = FetchType.LAZY, mappedBy = "interviewerId")
  @JsonIgnore
  private Set<InterviewerSlot> interviewerSlot;
}
