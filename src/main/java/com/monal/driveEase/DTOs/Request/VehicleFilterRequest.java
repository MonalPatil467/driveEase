package com.monal.driveEase.DTOs.Request;

import com.monal.driveEase.enums.FuelType;
import com.monal.driveEase.enums.Transmission;
import com.monal.driveEase.enums.VehicleType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VehicleFilterRequest {
    private String location;

    private VehicleType vehicleType;

    private FuelType fuelType;

    private Transmission transmission;

    private Double minPrice;

    private Double maxPrice;
}
