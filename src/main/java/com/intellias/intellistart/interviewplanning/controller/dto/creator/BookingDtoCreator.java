package com.intellias.intellistart.interviewplanning.controller.dto.creator;

import com.intellias.intellistart.interviewplanning.model.Booking;
import com.intellias.intellistart.interviewplanning.controller.dto.BookingDto;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;

/**
 * BookingDtoCreator class for creating booking DTOs from model entities.
 */
@Component
public class BookingDtoCreator {

  /**
   * Returns interviewerSlotDto from entity object.
   *
   * @param booking     booking entity
   * @return BookingDto
   */

  public BookingDto createBookingDto(Booking booking) {
    return BookingDto.builder()
        .id(booking.getId())
        .period(booking.getPeriod())
        .subject(booking.getSubject())
        .description(booking.getDescription())
        .build();
  }


  /**
   * Returns interviewerSlotDto from entity object.
   *
   * @param bookings set of booking entities
   * @return set of BookingDto
   */
  public Set<BookingDto> createBookingDtoSet(Set<Booking> bookings) {
    return bookings.stream()
        .map(this::createBookingDto)
        .collect(Collectors.toSet());
  }
}
