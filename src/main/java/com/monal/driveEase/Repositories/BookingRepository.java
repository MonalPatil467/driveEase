package com.monal.driveEase.Repositories;

import com.monal.driveEase.Entities.Booking;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookingRepository extends JpaRepository<Booking,Long> {
}
