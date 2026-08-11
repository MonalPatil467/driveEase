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
import com.monal.driveEase.enums.BookingStatus;
import com.monal.driveEase.enums.PaymentStatus;
import com.monal.driveEase.enums.Role;
import com.monal.driveEase.exception.BadRequestException;
import com.monal.driveEase.exception.ResourceNotFoundException;
import com.monal.driveEase.mappers.PaymentMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;
    private final BookingRepository bookingRepository;
    private final UserRepository userRepository;
    private final PaymentMapper paymentMapper;

    @Override
    public PaymentResponse makePayment(PaymentRequest request) {

        User customer = getAuthenticatedUser();

        if (customer.getRole() != Role.CUSTOMER) {
            throw new BadRequestException(
                    "Only customers can make payments."
            );
        }

        Booking booking = bookingRepository
                .findById(request.getBookingId())
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Booking not found"
                        )
                );

        if (!booking.getCustomer().getId()
                .equals(customer.getId())) {

            throw new BadRequestException(
                    "You can only pay for your own booking."
            );
        }

        if (booking.getPayment() != null) {
            throw new BadRequestException(
                    "Payment already completed."
            );
        }

        Payment payment = Payment.builder()
                .transactionId(UUID.randomUUID().toString())
                .amount(booking.getTotalAmount())
                .paymentMethod(request.getPaymentMethod())
                .paymentStatus(PaymentStatus.SUCCESS)
                .booking(booking)
                .build();

        booking.setBookingStatus(
                BookingStatus.CONFIRMED
        );

        Payment savedPayment =
                paymentRepository.save(payment);

        return paymentMapper.toResponse(savedPayment);
    }

    @Override
    public PaymentResponse getPaymentById(Long id) {

        User currentUser = getAuthenticatedUser();

        Payment payment = paymentRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Payment not found"
                        )
                );

        if (currentUser.getRole() == Role.ADMIN) {
            return paymentMapper.toResponse(payment);
        }

        if (currentUser.getRole() == Role.CUSTOMER
                && payment.getBooking()
                .getCustomer()
                .getId()
                .equals(currentUser.getId())) {

            return paymentMapper.toResponse(payment);
        }

        throw new BadRequestException(
                "You are not authorized to view this payment."
        );
    }

    @Override
    public List<PaymentResponse> getMyPayments() {

        User customer = getAuthenticatedUser();

        if (customer.getRole() != Role.CUSTOMER) {
            throw new BadRequestException(
                    "Only customers can view payments."
            );
        }

        return paymentRepository
                .findByBookingCustomerId(customer.getId())
                .stream()
                .map(paymentMapper::toResponse)
                .toList();
    }

    private User getAuthenticatedUser() {

        Authentication authentication =
                SecurityContextHolder
                        .getContext()
                        .getAuthentication();

        if (authentication == null
                || !authentication.isAuthenticated()) {

            throw new BadRequestException(
                    "User is not authenticated."
            );
        }

        String email = authentication.getName();

        return userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Authenticated user not found"
                        )
                );
    }
}