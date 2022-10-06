package com.intellias.intellistart.interviewplanning.repository;

import com.intellias.intellistart.interviewplanning.model.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * BookingRepository repository.
 */
@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {

}
