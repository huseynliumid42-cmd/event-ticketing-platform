package com.eventticketing.ticket;

import com.eventticketing.order.Order;
import com.eventticketing.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TicketRepository extends JpaRepository<Ticket,Long> {
    List<Ticket> findByOrder(Order order);

    List<Ticket> findByUser(User user);

    java.util.Optional<Ticket> findByTicketCode(String ticketCode);

    Long countByStatus(TicketStatus status);

    Long countByStatusIn(List<TicketStatus> statuses);
}
