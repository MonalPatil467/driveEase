package com.monal.driveEase.DTOs.Response;

import com.monal.driveEase.enums.BookingStatus;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookingResponse {
    private Long id;
    private String customerName;
    private String vehicleName;
    private LocalDate pickupDate;
    private LocalDate returnDate;
    private Double totalAmount;
    private BookingStatus bookingStatus;
}
