package com.eventticketing.order;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PreAuthorize("hasAnyRole('ATTENDEE', 'ADMIN', 'ORGANIZER')")
    @PostMapping
    public ResponseEntity<OrderResponse> createOrder(
            @Valid @RequestBody OrderRequest request,
            Authentication authentication
    ) {
        return ResponseEntity.ok(
                orderService.createOrder(request, authentication));
    }

    @PreAuthorize("hasAnyRole('ATTENDEE', 'ADMIN', 'ORGANIZER')")
    @GetMapping("/my")
    public ResponseEntity<List<OrderResponse>> getMyOrders(
            Authentication authentication
    ) {
        return ResponseEntity.ok(orderService.getMyOrders(authentication));
    }

}