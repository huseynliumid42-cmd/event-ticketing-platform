package com.eventticketing.payment;

import com.eventticketing.order.OrderResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/payments")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    @PostMapping("/checkout/{orderId}")
    public ResponseEntity<CheckoutResponse> createCheckoutSession(
            @PathVariable Long orderId
    ) {
        String checkoutUrl = paymentService.createCheckoutSession(orderId);

        return ResponseEntity.ok(
                new CheckoutResponse(checkoutUrl)
        );
    }

    @GetMapping("/success/{orderId}")
    public ResponseEntity<OrderResponse> markPaymentSuccess(
            @PathVariable Long orderId
    ) {
        return ResponseEntity.ok(
                paymentService.markOrderAsPaid(orderId)
        );
    }
}