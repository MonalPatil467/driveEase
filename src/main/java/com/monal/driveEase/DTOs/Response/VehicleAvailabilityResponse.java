package com.monal.driveEase.DTOs.Response;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VehicleAvailabilityResponse {
    private LocalDate pickupDate;

    private LocalDate returnDate;
}
