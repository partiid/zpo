package com.exam.booking.bookingservice.normal_model;

import lombok.*;

import java.time.LocalDate;

@AllArgsConstructor
@Getter
@Setter
public class ReservedTO {

    private Long id;
    private String customer;
    private String room;

    private LocalDate from;
    private LocalDate to;
}
