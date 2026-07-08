package com.eventticketing.analytics;

import com.eventticketing.order.OrderRepository;
import com.eventticketing.ticket.TicketRepository;
import com.eventticketing.ticket.TicketStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AnalyticsService {

    private final TicketRepository ticketRepository;
    private final OrderRepository orderRepository;

    public AnalyticsResponse getAnalytics() {

        
        Long ticketsSold = ticketRepository.countByStatusIn(
                List.of(TicketStatus.ACTIVE, TicketStatus.USED)
        );

        
        Long checkedInCount = ticketRepository.countByStatus(TicketStatus.USED);

        
        BigDecimal totalRevenue = orderRepository.calculateTotalRevenue();

        
        Double attendanceRate = 0.0;

        if (ticketsSold > 0) {
            attendanceRate =
                    (checkedInCount.doubleValue() / ticketsSold.doubleValue()) * 100;
        }

        return AnalyticsResponse.builder()
                .ticketsSold(ticketsSold)
                .checkedInCount(checkedInCount)
                .totalRevenue(totalRevenue)
                .attendanceRate(attendanceRate)
                .build();
    }
}