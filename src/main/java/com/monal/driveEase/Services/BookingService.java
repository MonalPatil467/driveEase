package com.monal.driveEase.Services;

import com.monal.driveEase.DTOs.Request.BookingRequest;
import com.monal.driveEase.DTOs.Response.BookingResponse;

import java.util.List;

public interface BookingService {
    BookingResponse createBooking(BookingRequest request);

    BookingResponse getBookingById(Long id);

    List<BookingResponse> getMyBookings();

    List<BookingResponse> getAllBookings();

    BookingResponse cancelBooking(Long bookingId);
}
