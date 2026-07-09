package com.monal.driveEase.Services;

import com.monal.driveEase.DTOs.Request.VehicleRequest;
import com.monal.driveEase.DTOs.Response.VehicleResponse;

import java.util.List;

public interface VehicleService {
    VehicleResponse addVehicle(VehicleRequest request);

    VehicleResponse updateVehicle(Long id, VehicleRequest request);

    void deleteVehicle(Long id);

    VehicleResponse getVehicleById(Long id);

    List<VehicleResponse> getAllVehicles();

    List<VehicleResponse> searchVehicles(String location);
}
