package com.eventticketing.ticket;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class TicketTierResponse {

    private Long id;

    private String name;

    private BigDecimal price;

    private Integer capacity;

    private LocalDateTime salesStart;

    private LocalDateTime salesEnd;

    private Long eventId;
}
