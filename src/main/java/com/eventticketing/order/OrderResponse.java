package com.eventticketing.order;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
public class OrderResponse {
    private Long id;
    private Long ticketTierId;
    private Integer quantity;
    private BigDecimal totalAmount;
    private OrderStatus status;
}
