package com.exam.booking.bookingservice.service;

import com.exam.booking.bookingservice.normal_model.ReservedTO;
import com.exam.booking.bookingservice.repository.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TableObjectService {

    @Autowired
    ReservationRepository reservationRepository;

    public List<ReservedTO> makeTO() {
        return reservationRepository.findAll().stream()
                .map(reservation -> new ReservedTO(reservation.getId(),
                        reservation.getCustomer().getName(),
                        reservation.getRoom().getNumber(),
                        reservation.getReservedFrom(),
                        reservation.getReservedTo()))
                .collect(Collectors.toList());
    }


}
