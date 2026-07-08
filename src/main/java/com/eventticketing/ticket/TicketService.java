package com.eventticketing.ticket;

import com.eventticketing.notification.EmailService;
import com.eventticketing.order.Order;
import com.eventticketing.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TicketService {

    private final TicketRepository ticketRepository;

    
    private final QrCodeService qrCodeService;

    
    private final EmailService emailService;

    public List<TicketResponse> createTicketsForPaidOrder(Order order) {

        
        List<TicketResponse> responses = new ArrayList<>();

        
        User user = order.getUser();

        
        for (int i = 0; i < order.getQuantity(); i++) {

            
            String ticketCode = generateTicketCode();

            
            String qrCodePath = qrCodeService.generateQrCode(ticketCode);

            
            Ticket ticket = Ticket.builder()
                    .ticketCode(ticketCode)
                    .qrCodeUrl(qrCodePath)
                    .status(TicketStatus.ACTIVE)
                    .createdAt(LocalDateTime.now())
                    .order(order)
                    .user(user)
                    .build();

            
            Ticket saved = ticketRepository.save(ticket);

            
            emailService.sendTicketEmail(
                    user.getEmail(),
                    saved.getTicketCode()
            );

            
            responses.add(mapToResponse(saved));
        }

        return responses;
    }

    public List<TicketResponse> getMyTickets(User user) {

        
        return ticketRepository.findByUser(user)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    private String generateTicketCode() {

        
        return "TICKET-" + UUID.randomUUID()
                .toString()
                .substring(0, 8)
                .toUpperCase();
    }

    private TicketResponse mapToResponse(Ticket ticket) {

        
        return TicketResponse.builder()
                .id(ticket.getId())
                .ticketCode(ticket.getTicketCode())
                .qrCodeUrl(ticket.getQrCodeUrl())
                .status(ticket.getStatus())
                .orderId(ticket.getOrder().getId())
                .userId(ticket.getUser().getId())
                .createdAt(ticket.getCreatedAt())
                .build();
    }
}