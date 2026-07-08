package com.eventticketing.order;

import com.eventticketing.ticket.TicketService;
import com.eventticketing.ticket.TicketTier;
import com.eventticketing.ticket.TicketTierRepository;
import com.eventticketing.user.User;
import com.eventticketing.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;

    
    private final TicketTierRepository ticketTierRepository;

    
    private final UserRepository userRepository;

    
    private final TicketService ticketService;

    public OrderResponse createOrder(OrderRequest request, Authentication authentication) {

        
        String email = authentication.getName();

        
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        
        TicketTier ticketTier = ticketTierRepository.findById(request.getTicketTierId())
                .orElseThrow(() -> new RuntimeException("Ticket tier not found"));

        
        BigDecimal totalAmount = ticketTier.getPrice()
                .multiply(BigDecimal.valueOf(request.getQuantity()));

        
        Order order = Order.builder()
                .quantity(request.getQuantity())
                .totalAmount(totalAmount)
                .status(OrderStatus.PENDING_PAYMENT)
                .createdAt(LocalDateTime.now())
                .ticketTier(ticketTier)
                .user(user)
                .build();

        
        Order saved = orderRepository.save(order);

        return mapToResponse(saved);
    }

    public List<OrderResponse> getMyOrders(Authentication authentication) {

        
        String email = authentication.getName();

        
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        
        return orderRepository.findByUser(user)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    public OrderResponse markOrderAsPaid(Long orderId) {

        
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        
        if (order.getStatus() == OrderStatus.PAID) {
            return mapToResponse(order);
        }

        
        order.setStatus(OrderStatus.PAID);

        
        Order saved = orderRepository.save(order);

        
        ticketService.createTicketsForPaidOrder(saved);

        return mapToResponse(saved);
    }

    private OrderResponse mapToResponse(Order order) {

        return OrderResponse.builder()
                .id(order.getId())
                .ticketTierId(order.getTicketTier().getId())
                .quantity(order.getQuantity())
                .totalAmount(order.getTotalAmount())
                .status(order.getStatus())
                .build();
    }
}