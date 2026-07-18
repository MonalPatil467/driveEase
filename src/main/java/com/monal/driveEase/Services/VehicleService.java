package com.monal.driveEase.Services;

import com.monal.driveEase.DTOs.Request.VehicleFilterRequest;
import com.monal.driveEase.DTOs.Request.VehicleRequest;
import com.monal.driveEase.DTOs.Response.VehicleAvailabilityResponse;
import com.monal.driveEase.DTOs.Response.VehicleResponse;
import org.springframework.data.domain.Page;

import java.util.List;

public interface VehicleService {
    VehicleResponse addVehicle(VehicleRequest request);

    VehicleResponse updateVehicle(Long id, VehicleRequest request);

    List<VehicleResponse> filterVehicles(VehicleFilterRequest request);

    void deleteVehicle(Long id);

    VehicleResponse getVehicleById(Long id);

    Page<VehicleResponse> getAllVehicles(int page, int size, String sortBy, String direction);

    List<VehicleResponse> searchVehicles(String location);
    //List<VehicleResponse> filterVehicles(VehicleFilterRequest request);

    List<VehicleAvailabilityResponse> getVehicleAvailability(Long vehicleId);
}
