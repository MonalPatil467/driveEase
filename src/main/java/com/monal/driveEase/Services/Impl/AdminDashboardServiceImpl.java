package com.monal.driveEase.Services.Impl;

import com.monal.driveEase.DTOs.Response.AdminDashboardResponse;
import com.monal.driveEase.Repositories.BookingRepository;
import com.monal.driveEase.Repositories.PaymentRepository;
import com.monal.driveEase.Repositories.UserRepository;
import com.monal.driveEase.Repositories.VehicleRepository;
import com.monal.driveEase.Services.AdminDashboardService;
import com.monal.driveEase.enums.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdminDashboardServiceImpl implements AdminDashboardService {
    private final UserRepository userRepository;
    private final VehicleRepository vehicleRepository;
    private final BookingRepository bookingRepository;
    private final PaymentRepository paymentRepository;
    @Override
    public AdminDashboardResponse getDashboard() {
        return AdminDashboardResponse.builder()
                .totalUsers(userRepository.count())
                .totalOwners(userRepository.countByRole(Role.OWNER))
                .totalCustomers(userRepository.countByRole(Role.CUSTOMER))
                .totalVehicles(vehicleRepository.count())
                .totalBookings(bookingRepository.count())
                .totalRevenue(paymentRepository.getTotalRevenue())
                .build();
    }
}
