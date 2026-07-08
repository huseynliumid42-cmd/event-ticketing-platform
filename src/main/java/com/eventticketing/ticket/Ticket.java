package com.eventticketing.ticket;

import com.eventticketing.order.Order;
import com.eventticketing.user.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity 
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Ticket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    
    @Column(unique = true, nullable = false)
    private String ticketCode;

    
    private String qrCodeUrl;

    
    @Enumerated(EnumType.STRING)
    private TicketStatus status;

    
    private LocalDateTime createdAt;

    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;
}