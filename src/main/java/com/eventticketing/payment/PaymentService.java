package com.eventticketing.payment;

import com.eventticketing.order.Order;
import com.eventticketing.order.OrderRepository;
import com.eventticketing.order.OrderResponse;
import com.eventticketing.order.OrderService;
import com.stripe.Stripe;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PaymentService {

    private final OrderRepository orderRepository;
    private final OrderService orderService;

    @Value("${stripe.secret-key}")
    private String secretKey;

    @Value("${stripe.success-url}")
    private String successUrl;

    @Value("${stripe.cancel-url}")
    private String cancelUrl;

    public String createCheckoutSession(Long orderId) {

        
        Stripe.apiKey = secretKey;

        
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        try {

            SessionCreateParams params = SessionCreateParams.builder()
                    .setMode(SessionCreateParams.Mode.PAYMENT)

                    
                    .setSuccessUrl(successUrl + "?orderId=" + order.getId())

                    
                    .setCancelUrl(cancelUrl + "?orderId=" + order.getId())

                    
                    .putMetadata("orderId", order.getId().toString())

                    .addLineItem(
                            SessionCreateParams.LineItem.builder()
                                    .setQuantity(order.getQuantity().longValue())
                                    .setPriceData(
                                            SessionCreateParams.LineItem.PriceData.builder()
                                                    .setCurrency("usd")
                                                    .setUnitAmount(
                                                            order.getTicketTier()
                                                                    .getPrice()
                                                                    .multiply(java.math.BigDecimal.valueOf(100))
                                                                    .longValue()
                                                    )
                                                    .setProductData(
                                                            SessionCreateParams.LineItem.PriceData.ProductData.builder()
                                                                    .setName(order.getTicketTier().getName() + " Ticket")
                                                                    .build()
                                                    )
                                                    .build()
                                    )
                                    .build()
                    )
                    .build();

            
            Session session = Session.create(params);

            
            return session.getUrl();

        } catch (Exception e) {
            throw new RuntimeException("Stripe checkout session could not be created");
        }
    }

    public OrderResponse markOrderAsPaid(Long orderId) {

        
        return orderService.markOrderAsPaid(orderId);
    }
}