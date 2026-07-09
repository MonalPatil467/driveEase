package com.monal.driveEase.mappers;

import com.monal.driveEase.DTOs.Request.VehicleRequest;
import com.monal.driveEase.DTOs.Response.VehicleResponse;
import com.monal.driveEase.Entities.Vehicle;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface VehicleMapper {
    Vehicle toEntity(VehicleRequest request);

    VehicleResponse toResponse(Vehicle vehicle);
}
