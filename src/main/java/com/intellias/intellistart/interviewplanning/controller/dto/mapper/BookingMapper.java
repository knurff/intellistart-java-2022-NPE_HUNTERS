package com.intellias.intellistart.interviewplanning.controller.dto.mapper;

import com.intellias.intellistart.interviewplanning.controller.dto.BookingDto;
import com.intellias.intellistart.interviewplanning.model.Booking;
import com.intellias.intellistart.interviewplanning.model.TimePeriod;
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
        .from(booking.getPeriod().getStartTime())
        .to(booking.getPeriod().getEndTime())
        .subject(booking.getSubject())
        .description(booking.getDescription())
        .interviewerSlotId(booking.getInterviewerSlot().getId())
        .candidateSlotId(booking.getCandidateSlot().getId())
        .build();
  }


  /**
   * Returns entity created from DTO object.
   *
   * @param bookingDto BookingDto
   * @return Booking
   */
  public Booking createBookingFromDto(BookingDto bookingDto) {
    return Booking.builder()
        .period(new TimePeriod(bookingDto.getFrom(), bookingDto.getTo()))
        .subject(bookingDto.getSubject())
        .description(bookingDto.getDescription())
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