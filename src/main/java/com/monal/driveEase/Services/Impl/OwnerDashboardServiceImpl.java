package com.monal.driveEase.Services.Impl;

import com.monal.driveEase.DTOs.Response.OwnerDashboardResponse;
import com.monal.driveEase.Entities.User;
import com.monal.driveEase.Repositories.*;
import com.monal.driveEase.Services.OwnerDashboardService;
import com.monal.driveEase.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OwnerDashboardServiceImpl implements OwnerDashboardService {
    private final UserRepository userRepository;
    private final VehicleRepository vehicleRepository;
    private final BookingRepository bookingRepository;
    private final ReviewRepository reviewRepository;
    private final PaymentRepository paymentRepository;
    @Override
    public OwnerDashboardResponse getDashboard() {
        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        User owner = userRepository.findByEmail(authentication.getName())
                .orElseThrow(() -> new ResourceNotFoundException("Owner not found"));

        return OwnerDashboardResponse.builder()
                .totalVehicles(vehicleRepository.countByOwner(owner))
                .totalBookings(bookingRepository.countByVehicleOwner(owner))
                .totalRevenue(paymentRepository.getOwnerRevenue(owner))
                .averageRating(reviewRepository.getAverageRating(owner))
                .build();
    }
}
