package com.eventticketing.payment;

import com.stripe.model.Event;
import com.stripe.model.checkout.Session;
import com.stripe.net.Webhook;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/payments/webhook")
@RequiredArgsConstructor
public class PaymentWebhookController {

    private final PaymentService paymentService;

    @Value("${stripe.webhook-secret}")
    private String webhookSecret;

    @PostMapping
    public ResponseEntity<String> handleStripeWebhook(
            @RequestBody String payload,
            @RequestHeader("Stripe-Signature") String sigHeader
    ) {
        try {
            Event event = Webhook.constructEvent(
                    payload,
                    sigHeader,
                    webhookSecret
            );

            System.out.println("Stripe event received: " + event.getType());

            if ("checkout.session.completed".equals(event.getType())) {

                Session session = (Session) event
                        .getDataObjectDeserializer()
                        .deserializeUnsafe();

                System.out.println("Session metadata: " + session.getMetadata());

                String orderIdString = session.getMetadata().get("orderId");

                if (orderIdString == null) {
                    System.out.println("Order ID metadata is null");
                    return ResponseEntity.badRequest().body("Order ID missing");
                }

                Long orderId = Long.valueOf(orderIdString);

                System.out.println("Marking order as paid: " + orderId);

                paymentService.markOrderAsPaid(orderId);
            }

            return ResponseEntity.ok("Webhook processed");

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest()
                    .body("Webhook error: " + e.getMessage());
        }
    }
}