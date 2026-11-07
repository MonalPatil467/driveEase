
package com.monal.driveEase.Repositories;

import com.monal.driveEase.Entities.Booking;
import com.monal.driveEase.Entities.User;
import com.monal.driveEase.enums.BookingStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface BookingRepository extends JpaRepository<Booking,Long> {
    List<Booking> findByVehicleId(Long vehicleId);

    List<Booking> findByCustomerId(Long id);

    Optional<Booking> findByCustomerIdAndVehicleId(Long customerId, Long vehicleId);

    long countByVehicleOwner(User owner);

    List<Booking> findByVehicleIdAndBookingStatus(
            Long vehicleId,
            BookingStatus bookingStatus);
    @Query("""
    SELECT COUNT(b)
    FROM Booking b
    WHERE b.vehicle.id = :vehicleId
      AND b.pickupDate <= :returnDate
      AND b.returnDate >= :pickupDate
      AND b.bookingStatus <> :cancelledStatus
""")
    long countOverlappingBookings(
            @Param("vehicleId") Long vehicleId,
            @Param("pickupDate") LocalDate pickupDate,
            @Param("returnDate") LocalDate returnDate,
            @Param("cancelledStatus") BookingStatus cancelledStatus
    );
}