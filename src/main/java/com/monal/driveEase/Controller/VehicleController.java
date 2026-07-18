package com.monal.driveEase.Controller;

import com.monal.driveEase.DTOs.Request.VehicleRequest;
import com.monal.driveEase.DTOs.Response.VehicleResponse;
import com.monal.driveEase.Services.VehicleService;
import lombok.RequiredArgsConstructor;
import com.monal.driveEase.DTOs.Request.VehicleFilterRequest;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/vehicles")
@RequiredArgsConstructor
public class VehicleController {
    private final VehicleService vehicleService;

    @PreAuthorize("hasRole('OWNER')")
    @PostMapping
    public ResponseEntity<VehicleResponse> addVehicle(@RequestBody VehicleRequest request) {
        return new ResponseEntity<>(vehicleService.addVehicle(request), HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('OWNER')")
    @PutMapping("/{id}")
    public ResponseEntity<VehicleResponse> updateVehicle(
            @PathVariable Long id,
            @RequestBody VehicleRequest request) {

        return ResponseEntity.ok(vehicleService.updateVehicle(id, request));
    }

    @PreAuthorize("hasRole('OWNER')")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteVehicle(@PathVariable Long id) {
        vehicleService.deleteVehicle(id);
        return ResponseEntity.ok("Vehicle deleted successfully.");
    }

    @GetMapping("/{id}")
    public ResponseEntity<VehicleResponse> getVehicleById(@PathVariable Long id) {
        return ResponseEntity.ok(vehicleService.getVehicleById(id));
    }

    @PostMapping("/filter")
    public ResponseEntity<List<VehicleResponse>> filterVehicles(
            @RequestBody VehicleFilterRequest request) {

        return ResponseEntity.ok(
                vehicleService.filterVehicles(request)
        );
    }

    @GetMapping
    public ResponseEntity<Page<VehicleResponse>> getAllVehicles(

            @RequestParam(defaultValue = "0") int page,

            @RequestParam(defaultValue = "5") int size,

            @RequestParam(defaultValue = "brand") String sortBy,

            @RequestParam(defaultValue = "asc") String direction) {

        return ResponseEntity.ok(
                vehicleService.getAllVehicles(page, size, sortBy, direction)
        );
    }

    @GetMapping("/search")
    public ResponseEntity<List<VehicleResponse>> searchVehicles(
            @RequestParam String location) {

        return ResponseEntity.ok(vehicleService.searchVehicles(location));
    }

}
