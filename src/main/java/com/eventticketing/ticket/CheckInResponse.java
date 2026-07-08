package com.eventticketing.ticket;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CheckInResponse {
    private String ticketCode;

    private TicketStatus status;

    private String message;
}
