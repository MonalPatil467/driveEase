package com.monal.driveEase.Controller;

import com.monal.driveEase.Entities.Booking;
import com.monal.driveEase.Entities.Payment;
import com.monal.driveEase.Repositories.BookingRepository;
import com.monal.driveEase.Repositories.PaymentRepository;
import com.monal.driveEase.enums.BookingStatus;
import com.monal.driveEase.enums.PaymentStatus;
import com.stripe.exception.SignatureVerificationException;
import com.stripe.model.Event;
import com.stripe.model.checkout.Session;
import com.stripe.net.Webhook;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/payments")
@RequiredArgsConstructor
public class PaymentWebhookController {

    private final PaymentRepository paymentRepository;
    private final BookingRepository bookingRepository;

    @Value("${stripe.webhook.secret}")
    private String webhookSecret;

    @PostMapping("/webhook")
    public ResponseEntity<String> handleWebhook(
            @RequestBody String payload,
            @RequestHeader("Stripe-Signature") String sigHeader) {

        Event event;

        try {
            event = Webhook.constructEvent(payload, sigHeader, webhookSecret);
        } catch (SignatureVerificationException e) {
            return ResponseEntity.badRequest().body("Invalid Stripe signature");
        }

        switch (event.getType()) {

            case "checkout.session.completed" -> {
                Session session = (Session) event.getDataObjectDeserializer()
                        .getObject().orElse(null);
                if (session != null) {
                    handleCheckoutCompleted(session);
                }
            }

            case "checkout.session.expired" -> {
                Session session = (Session) event.getDataObjectDeserializer()
                        .getObject().orElse(null);
                if (session != null) {
                    handleCheckoutExpired(session);
                }
            }

            default -> { /* ignore other event types */ }
        }

        return ResponseEntity.ok("Webhook received");
    }

    @Transactional
    protected void handleCheckoutCompleted(Session session) {

        Payment payment = paymentRepository
                .findByTransactionId(session.getId())
                .orElse(null);

        if (payment == null) {
            return; // unknown session — nothing to reconcile
        }

        // Idempotency guard: Stripe may deliver this event more than once.
        if (payment.getPaymentStatus() == PaymentStatus.SUCCESS) {
            return;
        }

        // Defense in depth: only confirm if Stripe itself says it was paid.
        if (!"paid".equals(session.getPaymentStatus())) {
            return;
        }

        payment.setPaymentStatus(PaymentStatus.SUCCESS);
        paymentRepository.save(payment);

        Booking booking = payment.getBooking();

        if (booking.getBookingStatus() == BookingStatus.PENDING) {
            booking.setBookingStatus(BookingStatus.CONFIRMED);
            bookingRepository.save(booking);
        }
    }

    @Transactional
    protected void handleCheckoutExpired(Session session) {

        Payment payment = paymentRepository
                .findByTransactionId(session.getId())
                .orElse(null);

        if (payment == null) {
            return;
        }

        if (payment.getPaymentStatus() == PaymentStatus.SUCCESS) {
            return;
        }

        payment.setPaymentStatus(PaymentStatus.FAILED);
        paymentRepository.save(payment);


    }
}