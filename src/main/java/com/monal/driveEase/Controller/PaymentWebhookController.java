package com.monal.driveEase.Controller;


import com.monal.driveEase.Entities.Payment;
import com.monal.driveEase.Repositories.PaymentRepository;
import com.monal.driveEase.enums.PaymentStatus;
import com.stripe.exception.SignatureVerificationException;
import com.stripe.model.Event;
import com.stripe.model.PaymentIntent;
import com.stripe.net.Webhook;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/payments")
@RequiredArgsConstructor
public class PaymentWebhookController {

    private final PaymentRepository paymentRepository;

    @Value("${stripe.webhook.secret}")
    private String webhookSecret;

    @PostMapping("/webhook")
    public ResponseEntity<String> handleWebhook(
            @RequestBody String payload,
            @RequestHeader("Stripe-Signature") String sigHeader
    ) {

        Event event;

        try {
            event = Webhook.constructEvent(
                    payload,
                    sigHeader,
                    webhookSecret
            );

        } catch (SignatureVerificationException e) {

            return ResponseEntity
                    .badRequest()
                    .body("Invalid Stripe signature");
        }

        switch (event.getType()) {

            case "payment_intent.succeeded":

                PaymentIntent succeededIntent =
                        (PaymentIntent) event
                                .getDataObjectDeserializer()
                                .getObject()
                                .orElse(null);

                if (succeededIntent != null) {
                    handlePaymentSuccess(succeededIntent);
                }

                break;

            case "payment_intent.payment_failed":

                PaymentIntent failedIntent =
                        (PaymentIntent) event
                                .getDataObjectDeserializer()
                                .getObject()
                                .orElse(null);

                if (failedIntent != null) {
                    handlePaymentFailure(failedIntent);
                }

                break;

            default:

                break;
        }

        return ResponseEntity.ok("Webhook received");
    }

    private void handlePaymentSuccess(
            PaymentIntent paymentIntent
    ) {

        Payment payment = paymentRepository
                .findByTransactionId(paymentIntent.getId())
                .orElse(null);

        if (payment == null) {
            return;
        }


        if (payment.getPaymentStatus() == PaymentStatus.SUCCESS) {
            return;
        }

        payment.setPaymentStatus(PaymentStatus.SUCCESS);

        paymentRepository.save(payment);
    }

    private void handlePaymentFailure(
            PaymentIntent paymentIntent
    ) {

        Payment payment = paymentRepository
                .findByTransactionId(paymentIntent.getId())
                .orElse(null);

        if (payment == null) {
            return;
        }

        payment.setPaymentStatus(PaymentStatus.FAILED);

        paymentRepository.save(payment);
    }
}
