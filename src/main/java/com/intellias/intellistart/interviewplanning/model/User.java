package com.intellias.intellistart.interviewplanning.model;

import com.intellias.intellistart.interviewplanning.role.UserRole;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "users")
public class User {
  @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private String email;
  private UserRole role;
  private int maxAmountOfInterviews;
}
