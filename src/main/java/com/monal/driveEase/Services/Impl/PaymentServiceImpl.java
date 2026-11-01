package com.monal.driveEase.Services.Impl;

import com.monal.driveEase.DTOs.Request.PaymentRequest;
import com.monal.driveEase.DTOs.Response.PaymentResponse;
import com.monal.driveEase.Entities.Booking;
import com.monal.driveEase.Entities.Payment;
import com.monal.driveEase.Entities.User;
import com.monal.driveEase.Repositories.BookingRepository;
import com.monal.driveEase.Repositories.PaymentRepository;
import com.monal.driveEase.Repositories.UserRepository;
import com.monal.driveEase.Services.PaymentService;
import com.monal.driveEase.enums.PaymentStatus;
import com.monal.driveEase.enums.Role;
import com.monal.driveEase.exception.BadRequestException;
import com.monal.driveEase.exception.ResourceNotFoundException;
import com.monal.driveEase.mappers.PaymentMapper;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;
    private final BookingRepository bookingRepository;
    private final UserRepository userRepository;
    private final PaymentMapper paymentMapper;

    @Value("${stripe.checkout.success-url}")
    private String successUrl;

    @Value("${stripe.checkout.cancel-url}")
    private String cancelUrl;

    @Override
    public PaymentResponse makePayment(PaymentRequest request) {

        User customer = getAuthenticatedUser();

        if (customer.getRole() != Role.CUSTOMER) {
            throw new BadRequestException("Only customers can make payments.");
        }

        Booking booking = bookingRepository.findById(request.getBookingId())
                .orElseThrow(() -> new ResourceNotFoundException("Booking not found"));

        if (!booking.getCustomer().getId().equals(customer.getId())) {
            throw new BadRequestException("You can only pay for your own booking.");
        }

        if (booking.getPayment() != null) {
            throw new BadRequestException("Payment already exists for this booking.");
        }

        try {

            // Backend calculates the amount — never trust the frontend.
            long amountInPaise = Math.round(booking.getTotalAmount() * 100);

            String resolvedSuccessUrl = successUrl.replace("{bookingId}", booking.getId().toString());

            SessionCreateParams params = SessionCreateParams.builder()
                    .setMode(SessionCreateParams.Mode.PAYMENT)
                    .setSuccessUrl(resolvedSuccessUrl)
                    .setCancelUrl(cancelUrl)
                    .putMetadata("bookingId", booking.getId().toString())
                    .putMetadata("customerId", customer.getId().toString())
                    .addLineItem(
                            SessionCreateParams.LineItem.builder()
                                    .setQuantity(1L)
                                    .setPriceData(
                                            SessionCreateParams.LineItem.PriceData.builder()
                                                    .setCurrency("inr")
                                                    .setUnitAmount(amountInPaise)
                                                    .setProductData(
                                                            SessionCreateParams.LineItem.PriceData.ProductData.builder()
                                                                    .setName("DriveEase booking #" + booking.getId())
                                                                    .build()
                                                    )
                                                    .build()
                                    )
                                    .build()
                    )
                    .build();

            Session session = Session.create(params);

            Payment payment = Payment.builder()
                    .transactionId(session.getId())   // maps webhook events back to this Payment
                    .amount(booking.getTotalAmount())
                    .paymentMethod(request.getPaymentMethod())
                    .paymentStatus(PaymentStatus.PENDING)
                    .booking(booking)
                    .build();

            Payment savedPayment = paymentRepository.save(payment);

            PaymentResponse response = paymentMapper.toResponse(savedPayment);
            response.setCheckoutUrl(session.getUrl());

            return response;

        } catch (StripeException e) {
            throw new BadRequestException("Unable to create Stripe checkout session: " + e.getMessage());
        }
    }

    @Override
    public PaymentResponse getPaymentById(Long id) {

        User currentUser = getAuthenticatedUser();

        Payment payment = paymentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Payment not found"));

        if (currentUser.getRole() == Role.ADMIN) {
            return paymentMapper.toResponse(payment);
        }

        if (currentUser.getRole() == Role.CUSTOMER
                && payment.getBooking().getCustomer().getId().equals(currentUser.getId())) {
            return paymentMapper.toResponse(payment);
        }

        throw new BadRequestException("You are not authorized to view this payment.");
    }

    @Override
    public List<PaymentResponse> getMyPayments() {

        User customer = getAuthenticatedUser();

        if (customer.getRole() != Role.CUSTOMER) {
            throw new BadRequestException("Only customers can view payments.");
        }

        return paymentRepository.findByBookingCustomerId(customer.getId())
                .stream()
                .map(paymentMapper::toResponse)
                .toList();
    }

    private User getAuthenticatedUser() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            throw new BadRequestException("User is not authenticated.");
        }

        String email = authentication.getName();

        return userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("Authenticated user not found"));
    }
}