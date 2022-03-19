package com.exam.booking.bookingservice.service;

import com.exam.booking.bookingservice.entity_model.Customer;
import com.exam.booking.bookingservice.entity_model.Reservation;
import com.exam.booking.bookingservice.entity_model.Room;
import com.exam.booking.bookingservice.repository.CustomerRepository;
import com.exam.booking.bookingservice.repository.ReservationRepository;
import com.exam.booking.bookingservice.repository.RoomRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.time.LocalDate;

@Service
@Slf4j
public class InitService {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private ReservationRepository reservationRepository;


    @PostConstruct
    public void init() {

        Customer c1 = customerRepository.save(new Customer(0l, "Jacek Juk"));
        Customer c2 = customerRepository.save(new Customer(0l, "Halina Żuk"));


        Room r1 = roomRepository.save(new Room(0l, "273", 2, 500, true));
        Room r2 = roomRepository.save(new Room(0l, "321", 2, 100, false));
        Room r3 = roomRepository.save(new Room(0l, "213", 3, 350, true));
        Room r4 = roomRepository.save(new Room(0l, "002", 3, 600, true));
        Room r5 = roomRepository.save(new Room(0l, "231", 2, 200, false));
        Room r6 = roomRepository.save(new Room(0l, "100", 3, 200, false));
        Room r7 = roomRepository.save(new Room(0l, "100", 2, 200, true));
        Room r8 = roomRepository.save(new Room(0l, "290", 3, 250, false));


        reservationRepository.save(new Reservation(0l, LocalDate.now(), LocalDate.now().plusDays(3), c1, r1));
        reservationRepository.save(new Reservation(0l, LocalDate.now(), LocalDate.now().plusDays(2), c2, r2));

    }


}
