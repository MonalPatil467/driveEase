package com.monal.driveEase.Repositories;

import com.monal.driveEase.Entities.User;
import com.monal.driveEase.Entities.Vehicle;
import com.monal.driveEase.enums.FuelType;
import com.monal.driveEase.enums.Transmission;
import com.monal.driveEase.enums.VehicleType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface VehicleRepository extends JpaRepository<Vehicle,Long> {
    List<Vehicle> findByLocationIgnoreCase(String location);
    long countByOwner(User owner);
    @Query("""
SELECT v FROM Vehicle v
WHERE
(:location IS NULL OR LOWER(v.location)=LOWER(:location))
AND (:vehicleType IS NULL OR v.vehicleType=:vehicleType)
AND (:fuelType IS NULL OR v.fuelType=:fuelType)
AND (:transmission IS NULL OR v.transmission=:transmission)
AND (:minPrice IS NULL OR v.pricePerDay>=:minPrice)
AND (:maxPrice IS NULL OR v.pricePerDay<=:maxPrice)
""")
    List<Vehicle> filterVehicles(
            @Param("location") String location,
            @Param("vehicleType") VehicleType vehicleType,
            @Param("fuelType") FuelType fuelType,
            @Param("transmission") Transmission transmission,
            @Param("minPrice") Double minPrice,
            @Param("maxPrice") Double maxPrice
    );
}
