package com.monal.driveEase.Repositories;

import com.monal.driveEase.Entities.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<Payment,Long> {
}
