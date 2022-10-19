package com.intellias.intellistart.interviewplanning.service;

import com.intellias.intellistart.interviewplanning.exception.BookingNotFoundException;
import com.intellias.intellistart.interviewplanning.repository.BookingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * BookingService service.
 */
@Service
public class BookingService {

  private final BookingRepository bookingRepository;

  @Autowired
  public BookingService(BookingRepository bookingRepository) {
    this.bookingRepository = bookingRepository;
  }

  /**
   * Deletes booking by id.
   *
   * @param bookingId long id of booking
   * @throws BookingNotFoundException if booking with {@code bookingId} is not found.
   */
  public boolean deleteBooking(Long bookingId) {
    checkThatBookingExists(bookingId);
    bookingRepository.deleteById(bookingId);
    return true;
  }

  private void checkThatBookingExists(Long bookingId) {
    if (bookingRepository.findById(bookingId).isEmpty()) {
      throw new BookingNotFoundException(
          String.format("Booking with id: %d not found", bookingId));
    }
  }

}
