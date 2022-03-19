package com.exam.booking.bookingservice.normal_model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ReservedRoom {

    private String name;

    private List<LocalDate> reservationDates;

}
