package com.intellias.intellistart.interviewplanning.service.factory;

import com.intellias.intellistart.interviewplanning.model.Booking;
import com.intellias.intellistart.interviewplanning.model.TimePeriod;
import java.time.LocalTime;

public class BookingFactory {

  private static final String TEST_SUBJECT = "testSubject";
  private static final String TEST_DESCRIPTION = "testDescription";

  public static Booking createBooking() {
    return new Booking();
  }

  public static Booking createBookingWithId() {
    Booking booking = new Booking();
    booking.setId(1L);
    return booking;
  }

  public static Booking createBookingWithTimePeriod(LocalTime startTime, LocalTime endTime) {
    Booking booking = new Booking();
    TimePeriod timePeriod = new TimePeriod(startTime, endTime);
    booking.setPeriod(timePeriod);
    booking.setSubject(TEST_SUBJECT);
    booking.setDescription(TEST_DESCRIPTION);
    return booking;
  }
}
