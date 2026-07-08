package com.eventticketing.order;

import com.eventticketing.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;
import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByUser(User user);

    @Query("select coalesce(sum(o.totalAmount), 0) from Order o where o.status = 'PAID'")
    BigDecimal calculateTotalRevenue();
}
