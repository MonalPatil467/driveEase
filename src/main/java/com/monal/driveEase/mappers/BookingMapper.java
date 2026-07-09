package com.monal.driveEase.mappers;

import com.monal.driveEase.DTOs.Request.BookingRequest;
import com.monal.driveEase.DTOs.Response.BookingResponse;
import com.monal.driveEase.Entities.Booking;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface BookingMapper {
    Booking toEntity(BookingRequest request);

    @Mapping(source = "customer.id", target = "customerId")
    @Mapping(source = "vehicle.id", target = "vehicleId")
    BookingResponse toResponse(Booking booking);
}
