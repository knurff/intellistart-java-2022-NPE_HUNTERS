package com.intellias.intellistart.interviewplanning.service.factory;

import com.intellias.intellistart.interviewplanning.model.Booking;

public class BookingFactory {

  public static Booking createBookingWithId() {
    Booking booking = new Booking();
    booking.setId(1L);
    return booking;
  }
}
