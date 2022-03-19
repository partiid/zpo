package com.exam.booking.bookingservice.repository;

import com.exam.booking.bookingservice.entity_model.Room;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoomRepository extends JpaRepository<Room, Long> {
}
