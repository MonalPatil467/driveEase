package com.monal.driveEase.Repositories;

import com.monal.driveEase.Entities.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import com.monal.driveEase.Entities.User;
import java.util.List;

public interface ReviewRepository extends JpaRepository<Review,Long> {
    List<Review> findByVehicleId(Long vehicleId);

    List<Review> findByCustomerId(Long customerId);

    @Query("""
SELECT COALESCE(AVG(r.rating),0)
FROM Review r
WHERE r.vehicle.owner=:owner
""")
    Double getAverageRating(User owner);

    boolean existsByCustomerIdAndVehicleId(Long customerId, Long vehicleId);
}
