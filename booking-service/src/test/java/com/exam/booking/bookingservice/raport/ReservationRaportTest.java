package com.exam.booking.bookingservice.raport;

import com.exam.booking.bookingservice.entity_model.Customer;
import com.exam.booking.bookingservice.entity_model.Reservation;
import com.exam.booking.bookingservice.entity_model.Room;
import com.exam.booking.bookingservice.normal_model.ReservedRoom;
import org.apache.tomcat.jni.Local;
import org.junit.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import static org.junit.Assert.*;

public class ReservationRaportTest {

    @Test
    public void reservedRooms() {

//        Customer c1 = new Customer(0l, "Jacek Juk");
//        Customer c2 = new Customer(1l, "Halina Żuk");
//
//
//        Room r1 = new Room(0l, "273", 2, 500, true);
//        Room r2 = new Room(1l, "321", 2, 100, false);
//
//        Reservation rs1 = new Reservation(0l, LocalDate.now(), LocalDate.now().plusDays(3), c1, r1);
//        Reservation rs2 = new Reservation(1l, LocalDate.now(), LocalDate.now().plusDays(2), c2, r2);
//
//        List<LocalDate> lr1 = new ArrayList<>();
//        lr1.add(rs1.getReservedFrom());
//
//        List<LocalDate> lr2 = new ArrayList<>();
//        lr1.add(rs2.getReservedFrom());
//
//        List<ReservedRoom> expected = new ArrayList<>();
//        expected.add(new ReservedRoom(r1.getNumber(), lr1));
//        expected.add(new ReservedRoom(r2.getNumber(), lr2));
//
//        List<Reservation> given = Arrays.asList(rs1, rs2);
//
//        ReservationRaport reservationRaport = new ReservationRaport();
//
//        List<ReservedRoom> testResult = reservationRaport.reservedRooms(given);
//
//        expected.sort(Comparator.comparing(ReservedRoom::getName));
//        testResult.sort(Comparator.comparing(ReservedRoom::getName));
//
//        assertEquals(expected, testResult);
    }
}