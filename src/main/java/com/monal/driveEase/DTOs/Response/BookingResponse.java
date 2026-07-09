package com.monal.driveEase.DTOs.Response;

import com.monal.driveEase.enums.BookingStatus;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookingResponse {

    private Long id;

    private LocalDate pickupDate;

    private LocalDate returnDate;

    private Double totalAmount;

    private BookingStatus bookingStatus;

    private LocalDateTime bookingDate;

    private Long customerId;

    private Long vehicleId;
}
