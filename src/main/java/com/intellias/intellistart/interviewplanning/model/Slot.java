package com.intellias.intellistart.interviewplanning.model;

import java.time.LocalDateTime;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Data;

/**
 * Slot entity.
 */
@Entity
@Data
@Table(name = "slots")
public class Slot {
  @Id @GeneratedValue(strategy = GenerationType.AUTO)
  protected Long id;
  protected Long ownerId;
  protected LocalDateTime fromTime;
  protected LocalDateTime toTime;
}
