package com.monal.driveEase.Repositories;

import com.monal.driveEase.Entities.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<Booking,Long> {
    List<Booking> findByVehicleId(Long vehicleId);

    List<Booking> findByCustomerId(Long id);
}
