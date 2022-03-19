package com.exam.booking.bookingservice.raport;


import com.exam.booking.bookingservice.entity_model.Reservation;
import com.exam.booking.bookingservice.normal_model.ReservedRoom;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReservationRaport {

    public List<ReservedRoom> reservedRooms(List<Reservation> reservations) {
        return reservations.stream()
                .collect(Collectors.groupingBy(Reservation::getRoom))
                .entrySet()
                .stream()
                .map(roomListEntry -> new ReservedRoom(roomListEntry.getKey().getNumber(), roomListEntry.getValue()
                        .stream()
                        .map(Reservation::getReservedFrom)
                        .collect(Collectors.toList())))
                .collect(Collectors.toList());
    }

}
