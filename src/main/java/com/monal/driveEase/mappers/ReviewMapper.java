package com.monal.driveEase.mappers;

import com.monal.driveEase.DTOs.Request.ReviewRequest;
import com.monal.driveEase.DTOs.Response.ReviewResponse;
import com.monal.driveEase.Entities.Review;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ReviewMapper {

    Review toEntity(ReviewRequest request);

    @Mapping(target = "customerName",
            expression = "java(review.getCustomer().getFirstName() + \" \" + review.getCustomer().getLastName())")
    ReviewResponse toResponse(Review review);
}
