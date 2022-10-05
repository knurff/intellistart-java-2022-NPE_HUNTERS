package com.intellias.intellistart.interviewplanning.model;

import java.time.LocalDateTime;

public abstract class Slot {
  protected Long id;
  protected Long ownerId;
  protected LocalDateTime from;
  protected LocalDateTime to;
}
