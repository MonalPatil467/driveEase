package com.monal.driveEase.Services.Impl;

import com.monal.driveEase.DTOs.Request.VehicleFilterRequest;
import com.monal.driveEase.DTOs.Request.VehicleRequest;
import com.monal.driveEase.DTOs.Response.VehicleAvailabilityResponse;
import com.monal.driveEase.DTOs.Response.VehicleResponse;
import com.monal.driveEase.Entities.Booking;
import com.monal.driveEase.Entities.User;
import com.monal.driveEase.Entities.Vehicle;
import com.monal.driveEase.Repositories.BookingRepository;
import com.monal.driveEase.Repositories.UserRepository;
import com.monal.driveEase.Repositories.VehicleRepository;
import com.monal.driveEase.Services.VehicleService;
import com.monal.driveEase.enums.BookingStatus;
import com.monal.driveEase.exception.ResourceNotFoundException;
import com.monal.driveEase.exception.UnauthorizedException;
import com.monal.driveEase.mappers.VehicleMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import com.monal.driveEase.enums.Role;

@Service
@RequiredArgsConstructor
public class VehicleServiceImpl implements VehicleService {
    private final VehicleRepository vehicleRepository;
    private final UserRepository userRepository;
    private final VehicleMapper vehicleMapper;
    private final BookingRepository bookingRepository;
    @Override
    public VehicleResponse addVehicle(VehicleRequest request) {
        Authentication authentication = SecurityContextHolder
                .getContext()
                .getAuthentication();

        String email = authentication.getName();

        User owner = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Owner not found"));

        if (owner.getRole() != Role.OWNER) {
            throw new UnauthorizedException("Only owners can add vehicles.");
           // throw new RuntimeException("Only owners can add vehicles.");
        }

        Vehicle vehicle = vehicleMapper.toEntity(request);

        vehicle.setOwner(owner);
        vehicle.setAvailable(true);

        Vehicle savedVehicle = vehicleRepository.save(vehicle);

        return vehicleMapper.toResponse(savedVehicle);
    }

    @Override
    public VehicleResponse updateVehicle(Long id, VehicleRequest request) {
        Vehicle vehicle = vehicleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Vehicle not found"));
               // .orElseThrow(() -> new RuntimeException("Vehicle not found"));

        vehicle.setBrand(request.getBrand());
        vehicle.setModel(request.getModel());
        vehicle.setRegistrationNumber(request.getRegistrationNumber());
        vehicle.setManufacturingYear(request.getManufacturingYear());
        vehicle.setColor(request.getColor());
        vehicle.setVehicleType(request.getVehicleType());
        vehicle.setFuelType(request.getFuelType());
        vehicle.setTransmission(request.getTransmission());
        vehicle.setSeatingCapacity(request.getSeatingCapacity());
        vehicle.setPricePerDay(request.getPricePerDay());
        vehicle.setLocation(request.getLocation());
        vehicle.setDescription(request.getDescription());
        vehicle.setImageUrl(request.getImageUrl());

        Vehicle updatedVehicle = vehicleRepository.save(vehicle);

        return vehicleMapper.toResponse(updatedVehicle);
    }

    @Override
    public List<VehicleResponse> filterVehicles(VehicleFilterRequest request) {

        return vehicleRepository.filterVehicles(
                        request.getLocation(),
                        request.getVehicleType(),
                        request.getFuelType(),
                        request.getTransmission(),
                        request.getMinPrice(),
                        request.getMaxPrice()
                )
                .stream()
                .map(vehicleMapper::toResponse)
                .toList();
    }

    @Override
    public List<VehicleAvailabilityResponse> getVehicleAvailability(Long vehicleId) {

        Vehicle vehicle = vehicleRepository.findById(vehicleId)
                .orElseThrow(() ->
                        new RuntimeException("Vehicle not found"));

        List<Booking> bookings =
                bookingRepository.findByVehicleIdAndBookingStatus(
                        vehicle.getId(),
                        BookingStatus.CONFIRMED
                );

        return bookings.stream()
                .map(booking ->
                        VehicleAvailabilityResponse.builder()
                                .pickupDate(booking.getPickupDate())
                                .returnDate(booking.getReturnDate())
                                .build())
                .toList();
    }

    @Override
    public void deleteVehicle(Long id) {
        Vehicle vehicle = vehicleRepository.findById(id)
                //.orElseThrow(() -> new RuntimeException("Vehicle not found"));
                .orElseThrow(() -> new ResourceNotFoundException("Owner not found"));

        vehicleRepository.delete(vehicle);
    }

    @Override
    public VehicleResponse getVehicleById(Long id) {
        Vehicle vehicle=vehicleRepository.findById(id).orElseThrow(()->new RuntimeException("vehicle not found"));
        return vehicleMapper.toResponse(vehicle);
    }

    @Override
    public Page<VehicleResponse> getAllVehicles(int page, int size, String sortBy, String direction) {

        Sort sort = direction.equalsIgnoreCase("desc")
                ? Sort.by(sortBy).descending()
                : Sort.by(sortBy).ascending();

        Pageable pageable = PageRequest.of(page, size, sort);

        return vehicleRepository.findAll(pageable)
                .map(vehicleMapper::toResponse);
    }

    @Override
    public List<VehicleResponse> searchVehicles(String location) {
        return vehicleRepository.findByLocationIgnoreCase(location)
                .stream()
                .map(vehicleMapper::toResponse)
                .toList();
    }
}
