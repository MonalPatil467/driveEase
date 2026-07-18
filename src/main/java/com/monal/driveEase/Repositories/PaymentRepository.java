package com.monal.driveEase.Repositories;

import com.monal.driveEase.Entities.Payment;
import com.monal.driveEase.Entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Arrays;
import java.util.List;

public interface PaymentRepository extends JpaRepository<Payment,Long> {
    List<Payment> findByBookingCustomerId(Long customerId);

    @Query("""
SELECT COALESCE(SUM(p.amount),0)
FROM Payment p
WHERE p.paymentStatus='SUCCESS'
""")
    Double getTotalRevenue();

    @Query("""
SELECT COALESCE(SUM(p.amount),0)
FROM Payment p
WHERE p.booking.vehicle.owner = :owner
AND p.paymentStatus='SUCCESS'
""")
    Double getOwnerRevenue(User owner);
}
