package com.monal.driveEase.mappers;

import com.monal.driveEase.DTOs.Request.PaymentRequest;
import com.monal.driveEase.DTOs.Response.PaymentResponse;
import com.monal.driveEase.Entities.Payment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PaymentMapper {
    Payment toEntity(PaymentRequest request);

    @Mapping(source = "booking.id", target = "bookingId")
    PaymentResponse toResponse(Payment payment);
}
