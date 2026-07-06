package com.monal.driveEase.DTOs.Request;

import com.monal.driveEase.enums.PaymentMethod;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PaymentRequest {
    private Long bookingId;
    private PaymentMethod paymentMethod;
}
