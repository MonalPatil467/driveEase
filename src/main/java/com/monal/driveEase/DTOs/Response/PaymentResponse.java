package com.monal.driveEase.DTOs.Response;

import com.monal.driveEase.enums.PaymentMethod;
import com.monal.driveEase.enums.PaymentStatus;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PaymentResponse {
    private Long id;
    private String transactionId;
    private Double amount;
    private PaymentMethod paymentMethod;
    private PaymentStatus paymentStatus;
}
