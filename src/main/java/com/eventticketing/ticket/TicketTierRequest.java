package com.eventticketing.ticket;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
public class TicketTierRequest {

    private String name;

    private BigDecimal price;

    private Integer capacity;

    private LocalDateTime salesStart;

    private LocalDateTime salesEnd;
}
