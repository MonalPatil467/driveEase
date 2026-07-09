package com.monal.driveEase.Services;

import com.monal.driveEase.DTOs.Request.PaymentRequest;
import com.monal.driveEase.DTOs.Response.PaymentResponse;

import java.util.List;

public interface PaymentService {
    PaymentResponse makePayment(PaymentRequest request);

    PaymentResponse getPaymentById(Long id);

    List<PaymentResponse> getMyPayments();
}
