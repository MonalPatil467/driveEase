package com.monal.driveEase.Services.Impl;

import com.monal.driveEase.DTOs.Request.ReviewRequest;
import com.monal.driveEase.DTOs.Response.ReviewResponse;
import com.monal.driveEase.Entities.Booking;
import com.monal.driveEase.Entities.Review;
import com.monal.driveEase.Entities.User;
import com.monal.driveEase.Entities.Vehicle;
import com.monal.driveEase.Repositories.BookingRepository;
import com.monal.driveEase.Repositories.ReviewRepository;
import com.monal.driveEase.Repositories.UserRepository;
import com.monal.driveEase.Repositories.VehicleRepository;
import com.monal.driveEase.Services.ReviewService;
import com.monal.driveEase.enums.BookingStatus;
import com.monal.driveEase.enums.Role;
import com.monal.driveEase.exception.BadRequestException;
import com.monal.driveEase.exception.ResourceNotFoundException;
import com.monal.driveEase.mappers.ReviewMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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


        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        String email = authentication.getName();

        User customer = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        if (customer.getRole() != Role.CUSTOMER) {
            throw new BadRequestException("Only customers can add reviews.");
        }

        Vehicle vehicle = vehicleRepository.findById(request.getVehicleId())
                .orElseThrow(() -> new ResourceNotFoundException("Vehicle not found"));

        Booking booking = bookingRepository
                .findByCustomerIdAndVehicleId(customer.getId(), vehicle.getId())
                .orElseThrow(() -> new BadRequestException("You must book this vehicle before reviewing."));

        if (booking.getBookingStatus() != BookingStatus.CONFIRMED) {
            throw new BadRequestException("Only confirmed bookings can be reviewed.");
        }

        if (reviewRepository.existsByCustomerIdAndVehicleId(customer.getId(), vehicle.getId())) {
            throw new BadRequestException("You have already reviewed this vehicle.");
        }

        Review review = reviewMapper.toEntity(request);

        review.setCustomer(customer);
        review.setVehicle(vehicle);

        Review savedReview = reviewRepository.save(review);

        return reviewMapper.toResponse(savedReview);
    }

    @Override
    public ReviewResponse updateReview(Long reviewId, ReviewRequest request) {
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new ResourceNotFoundException("Review not found"));

        review.setRating(request.getRating());
        review.setComment(request.getComment());

        Review updatedReview = reviewRepository.save(review);

        return reviewMapper.toResponse(updatedReview);
    }

    @Override
    public void deleteReview(Long reviewId) {
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new ResourceNotFoundException("Review not found"));

        reviewRepository.delete(review);
    }

    @Override
    public List<ReviewResponse> getReviewsByVehicle(Long vehicleId) {
        Vehicle vehicle = vehicleRepository.findById(vehicleId)
                .orElseThrow(() -> new ResourceNotFoundException("Vehicle not found"));

        return reviewRepository.findByVehicleId(vehicle.getId())
                .stream()
                .map(reviewMapper::toResponse)
                .toList();
    }

    @Override
    public List<ReviewResponse> getMyReviews() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        String email = authentication.getName();

        User customer = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        return reviewRepository.findByCustomerId(customer.getId())
                .stream()
                .map(reviewMapper::toResponse)
                .toList();
    }
}
