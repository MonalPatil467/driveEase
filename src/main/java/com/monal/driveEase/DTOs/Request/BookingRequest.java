package com.monal.driveEase.DTOs.Request;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookingRequest {
    private Long vehicleId;

    private LocalDate pickupDate;

    private LocalDate returnDate;
}
