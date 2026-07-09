package com.monal.driveEase.Services.Impl;

import com.monal.driveEase.DTOs.Request.ReviewRequest;
import com.monal.driveEase.DTOs.Response.ReviewResponse;
import com.monal.driveEase.Repositories.BookingRepository;
import com.monal.driveEase.Repositories.ReviewRepository;
import com.monal.driveEase.Repositories.UserRepository;
import com.monal.driveEase.Repositories.VehicleRepository;
import com.monal.driveEase.Services.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {
    private final ReviewRepository reviewRepository;
    private final VehicleRepository vehicleRepository;
    private final BookingRepository bookingRepository;
    private final UserRepository userRepository;
    private final ReviewMapper reviewMapper;
    @Override
    public ReviewResponse addReview(ReviewRequest request) {
        return null;
    }

    @Override
    public ReviewResponse updateReview(Long reviewId, ReviewRequest request) {
        return null;
    }

    @Override
    public void deleteReview(Long reviewId) {

    }

    @Override
    public List<ReviewResponse> getReviewsByVehicle(Long vehicleId) {
        return List.of();
    }

    @Override
    public List<ReviewResponse> getMyReviews() {
        return List.of();
    }
}
