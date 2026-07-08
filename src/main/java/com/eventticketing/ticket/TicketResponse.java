package com.eventticketing.ticket;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class TicketResponse {
    private Long id;

    private String ticketCode;

    private String qrCodeUrl;

    private TicketStatus status;

    private Long orderId;

    private Long userId;

    private LocalDateTime createdAt;
}
