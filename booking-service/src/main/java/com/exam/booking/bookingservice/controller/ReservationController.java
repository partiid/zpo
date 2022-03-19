package com.exam.booking.bookingservice.controller;


import com.exam.booking.bookingservice.normal_model.ReservedRoom;
import com.exam.booking.bookingservice.raport.ReservationRaport;
import com.exam.booking.bookingservice.repository.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "/api/")
public class ReservationController {

    @Autowired
    private ReservationRaport reservationRaport;

    @Autowired
    private ReservationRepository reservationRepository;

    @RequestMapping(path = "reservedRooms")
    public List<ReservedRoom> allReservedRooms(){
        return reservationRaport.reservedRooms(reservationRepository.findAll());
    }

}
