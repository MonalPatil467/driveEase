package com.monal.driveEase.DTOs.Response;

import com.monal.driveEase.enums.FuelType;
import com.monal.driveEase.enums.Transmission;
import com.monal.driveEase.enums.VehicleType;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VehicleResponse {
    private Long id;
    private String brand;
    private String model;
    private String registrationNumber;
    private Integer manufacturingYear;
    private String color;
    private VehicleType vehicleType;
    private FuelType fuelType;
    private Transmission transmission;
    private Integer seatingCapacity;
    private Double pricePerDay;
    private String location;
    private String description;
    private Boolean available;
    private String imageUrl;
}
