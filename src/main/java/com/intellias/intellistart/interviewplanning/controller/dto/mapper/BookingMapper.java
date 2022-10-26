package com.intellias.intellistart.interviewplanning.controller.dto.mapper;

import com.intellias.intellistart.interviewplanning.controller.dto.BookingDto;
import com.intellias.intellistart.interviewplanning.model.Booking;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;

/**
 * BookingSlotsDtoMapper class for mapping interviewer slots DTOs from model entities and vice
 * versa.
 */
@Component
public class BookingMapper {

  /**
   * Returns BookingDto from entity object.
   *
   * @param booking booking entity
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
   * Returns BookingDto from entity object.
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