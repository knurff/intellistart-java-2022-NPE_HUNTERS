package com.intellias.intellistart.interviewplanning.service;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import com.intellias.intellistart.interviewplanning.exception.BookingNotFoundException;
import com.intellias.intellistart.interviewplanning.model.Booking;
import com.intellias.intellistart.interviewplanning.repository.BookingRepository;
import com.intellias.intellistart.interviewplanning.service.factory.BookingFactory;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class BookingServiceTest {

  @Mock
  private BookingRepository bookingRepository;
  private BookingService bookingService;

  @BeforeEach
  void setup() {
    bookingService = new BookingService(bookingRepository);
  }

  @Test
  void deleteBookingWorkingProperly() {
    Booking booking = BookingFactory.createBookingWithId();

    when(bookingRepository.findById(1L)).thenReturn(Optional.of(booking));

    assertTrue(bookingService.deleteBooking(1L));
  }

  @Test
  void deleteBookingThrowsAnExceptionIfBookingNotExists() {

    assertThrows(BookingNotFoundException.class, () ->
        bookingService.deleteBooking(1L));
  }

}
