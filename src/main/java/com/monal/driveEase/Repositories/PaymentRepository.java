package com.monal.driveEase.Repositories;

import com.monal.driveEase.Entities.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Arrays;
import java.util.List;

public interface PaymentRepository extends JpaRepository<Payment,Long> {
    List<Payment> findByBookingCustomerId(Long customerId);
}
