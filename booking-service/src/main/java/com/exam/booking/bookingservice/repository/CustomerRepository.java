package com.exam.booking.bookingservice.repository;

import com.exam.booking.bookingservice.entity_model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
}
