package com.monal.driveEase.Services.Impl;

import com.monal.driveEase.DTOs.Request.BookingRequest;
import com.monal.driveEase.DTOs.Response.BookingResponse;
import com.monal.driveEase.Entities.Booking;
import com.monal.driveEase.Entities.User;
import com.monal.driveEase.Entities.Vehicle;
import com.monal.driveEase.Repositories.BookingRepository;
import com.monal.driveEase.Repositories.UserRepository;
import com.monal.driveEase.Repositories.VehicleRepository;
import com.monal.driveEase.Services.BookingService;
import com.monal.driveEase.enums.BookingStatus;
import com.monal.driveEase.enums.Role;
import com.monal.driveEase.mappers.BookingMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.awt.print.Book;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BookingServiceImpl implements BookingService {
    private final BookingRepository bookingRepository;
    private final UserRepository userRepository;
    private final VehicleRepository vehicleRepository;
    private final BookingMapper bookingMapper;

    @Override
    public BookingResponse createBooking(BookingRequest request) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        String email = authentication.getName();

        User customer = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Customer not found"));

        if (customer.getRole() != Role.CUSTOMER) {
            throw new RuntimeException("Only customers can book vehicles.");
        }

        Vehicle vehicle = vehicleRepository.findById(request.getVehicleId())
                .orElseThrow(() -> new RuntimeException("Vehicle not found"));

        if (request.getPickupDate().isAfter(request.getReturnDate())) {
            throw new RuntimeException("Invalid booking dates.");
        }

        List<Booking> existingBookings =
                bookingRepository.findByVehicleId(vehicle.getId());

        for (Booking booking : existingBookings) {

            boolean overlap = !(request.getReturnDate().isBefore(booking.getPickupDate())
                    || request.getPickupDate().isAfter(booking.getReturnDate()));

            if (overlap &&
                    booking.getBookingStatus() != BookingStatus.CANCELLED) {

                throw new RuntimeException("Vehicle is already booked for these dates.");
            }
        }

        long totalDays =
                ChronoUnit.DAYS.between(request.getPickupDate(), request.getReturnDate()) + 1;

        double totalAmount = totalDays * vehicle.getPricePerDay();

        Booking booking = Booking.builder()
                .pickupDate(request.getPickupDate())
                .returnDate(request.getReturnDate())
                .customer(customer)
                .vehicle(vehicle)
                .totalAmount(totalAmount)
                .bookingStatus(BookingStatus.CONFIRMED)
                .build();

        Booking savedBooking = bookingRepository.save(booking);

        return bookingMapper.toResponse(savedBooking);
    }

    @Override
    public BookingResponse getBookingById(Long id) {
        Booking booking = bookingRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Booking not found"));

        return bookingMapper.toResponse(booking);
    }

    @Override
    public List<BookingResponse> getMyBookings() {
        Authentication authentication = SecurityContextHolder
                .getContext()
                .getAuthentication();

        String email = authentication.getName();

        User customer = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (customer.getRole() != Role.CUSTOMER) {
            throw new RuntimeException("Only customers can view their bookings.");
        }

        List<Booking> bookings = bookingRepository.findByCustomerId(customer.getId());

        return bookings.stream()
                .map(bookingMapper::toResponse)
                .toList();
    }

    @Override
    public List<BookingResponse> getAllBookings() {
        Authentication authentication = SecurityContextHolder
                .getContext()
                .getAuthentication();

        String email = authentication.getName();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (user.getRole() != Role.ADMIN) {
            throw new RuntimeException("Only admin can view all bookings.");
        }

        List<Booking> bookings = bookingRepository.findAll();

        return bookings.stream()
                .map(bookingMapper::toResponse)
                .toList();
    }

    @Override
    public BookingResponse cancelBooking(Long bookingId) {
        Authentication authentication = SecurityContextHolder
                .getContext()
                .getAuthentication();

        String email = authentication.getName();

        User customer = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (customer.getRole() != Role.CUSTOMER) {
            throw new RuntimeException("Only customers can cancel bookings.");
        }
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new RuntimeException("Booking not found"));
        if (!booking.getCustomer().getId().equals(customer.getId())) {
            throw new RuntimeException("You can only cancel your own bookings.");
        }
        if (booking.getBookingStatus() == BookingStatus.CANCELLED) {
            throw new RuntimeException("Booking is already cancelled.");
        }

        booking.setBookingStatus(BookingStatus.CANCELLED);

        Booking cancelledBooking = bookingRepository.save(booking);

        return bookingMapper.toResponse(cancelledBooking);
    }
}
