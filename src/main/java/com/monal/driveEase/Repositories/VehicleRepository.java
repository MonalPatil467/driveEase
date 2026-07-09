package com.monal.driveEase.Repositories;

import com.monal.driveEase.Entities.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VehicleRepository extends JpaRepository<Vehicle,Long> {
    List<Vehicle> findByLocationIgnoreCase(String location);
}
