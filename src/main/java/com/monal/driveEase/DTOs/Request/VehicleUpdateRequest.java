package com.monal.driveEase.DTOs.Request;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VehicleUpdateRequest {
    private Double pricePerDay;
    private String location;
    private String description;
    private Boolean available;
}
