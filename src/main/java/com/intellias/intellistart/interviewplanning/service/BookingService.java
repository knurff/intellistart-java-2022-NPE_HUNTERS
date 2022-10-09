package com.intellias.intellistart.interviewplanning.service;

import com.intellias.intellistart.interviewplanning.repository.BookingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * BookingService service.
 */
@Service
public class BookingService {

  @Autowired
  private BookingRepository bookingRepository;

}
