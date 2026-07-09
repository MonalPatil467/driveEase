package com.monal.driveEase.Services;

import com.monal.driveEase.DTOs.Request.ReviewRequest;
import com.monal.driveEase.DTOs.Response.ReviewResponse;

import java.util.List;

public interface ReviewService {
    ReviewResponse addReview(ReviewRequest request);

    ReviewResponse updateReview(Long reviewId, ReviewRequest request);

    void deleteReview(Long reviewId);

    List<ReviewResponse> getReviewsByVehicle(Long vehicleId);

    List<ReviewResponse> getMyReviews();
}
