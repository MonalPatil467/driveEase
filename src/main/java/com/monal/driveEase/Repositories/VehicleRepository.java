package com.monal.driveEase.Repositories;

import com.monal.driveEase.Entities.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VehicleRepository extends JpaRepository<Vehicle,Long> {
}
