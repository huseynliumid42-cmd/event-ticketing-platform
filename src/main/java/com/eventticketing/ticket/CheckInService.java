package com.eventticketing.ticket;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CheckInService {

    private final TicketRepository ticketRepository;

    
    private final CheckInLockService checkInLockService;

    
    private final DashboardSseService dashboardSseService;

    public CheckInResponse checkIn(String ticketCode) {

        boolean locked = checkInLockService.acquireLock(ticketCode);

        if (!locked) {
            return CheckInResponse.builder()
                    .ticketCode(ticketCode)
                    .message("Ticket is being processed")
                    .build();
        }

        try {
            Ticket ticket = ticketRepository.findByTicketCode(ticketCode)
                    .orElseThrow(() -> new RuntimeException("Ticket not found"));

            if (ticket.getStatus() == TicketStatus.USED) {
                return CheckInResponse.builder()
                        .ticketCode(ticket.getTicketCode())
                        .status(ticket.getStatus())
                        .message("Ticket already used")
                        .build();
            }

            if (ticket.getStatus() == TicketStatus.CANCELLED) {
                return CheckInResponse.builder()
                        .ticketCode(ticket.getTicketCode())
                        .status(ticket.getStatus())
                        .message("Ticket is cancelled")
                        .build();
            }

            ticket.setStatus(TicketStatus.USED);

            Ticket saved = ticketRepository.save(ticket);

            
            Long checkedInCount = ticketRepository.countByStatus(TicketStatus.USED);

            
            dashboardSseService.sendCheckInUpdate(checkedInCount);

            return CheckInResponse.builder()
                    .ticketCode(saved.getTicketCode())
                    .status(saved.getStatus())
                    .message("Check-in successful")
                    .build();

        } finally {
            checkInLockService.releaseLock(ticketCode);
        }
    }
}