package com.exam.booking.bookingservice.repository;

import com.exam.booking.bookingservice.entity_model.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {
}
