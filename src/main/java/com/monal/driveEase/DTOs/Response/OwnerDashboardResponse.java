package com.monal.driveEase.DTOs.Response;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OwnerDashboardResponse {
    private Long totalVehicles;
    private Long totalBookings;
    private Double totalRevenue;
    private Double averageRating;
}
