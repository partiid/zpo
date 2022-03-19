package com.exam.booking.bookingservice.entity_model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "RESERVATIONS")
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    private Long id;

    @NotNull
    private LocalDate reservedFrom;

    @NotNull
    private LocalDate reservedTo;

    @ManyToOne
    private Customer customer;

    @ManyToOne
    private Room room;




}
