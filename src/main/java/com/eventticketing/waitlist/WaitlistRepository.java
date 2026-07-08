package com.eventticketing.waitlist;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface WaitlistRepository extends JpaRepository<Waitlist, Long> {

    List<Waitlist> findByEventIdAndStatusOrderByCreatedAtAsc(
            Long eventId,
            WaitlistStatus status
    );

    Optional<Waitlist> findByEventIdAndUserId(Long eventId, Long userId);

    boolean existsByEventIdAndUserIdAndStatus(
            Long eventId,
            Long userId,
            WaitlistStatus status
    );
}