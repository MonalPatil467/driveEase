package com.monal.driveEase.DTOs.Response;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AdminDashboardResponse {
    private Long totalUsers;
    private Long totalOwners;
    private Long totalCustomers;
    private Long totalVehicles;
    private Long totalBookings;
    private Double totalRevenue;
}
