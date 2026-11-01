package com.monal.driveEase.DTOs.Response;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NaturalSearchResponse {


    private String interpretation;

    private String location;
    private String vehicleType;
    private String fuelType;
    private String transmission;
    private Double minPrice;
    private Double maxPrice;
    private Integer minSeats;
    private String pickupDate;
    private String returnDate;

    private List<VehicleResponse> vehicles;
}