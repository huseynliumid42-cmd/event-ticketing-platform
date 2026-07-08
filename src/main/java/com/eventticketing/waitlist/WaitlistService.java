package com.eventticketing.waitlist;

import com.eventticketing.event.Event;
import com.eventticketing.event.EventRepository;
import com.eventticketing.notification.EmailService;
import com.eventticketing.user.User;
import com.eventticketing.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class WaitlistService {

    private final WaitlistRepository waitlistRepository;
    private final EventRepository eventRepository;
    private final UserRepository userRepository;
    private final EmailService emailService;

    public WaitlistResponse joinWaitlist(Long eventId, Authentication authentication) {
        String email = authentication.getName();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new RuntimeException("Event not found"));

        boolean alreadyWaiting = waitlistRepository.existsByEventIdAndUserIdAndStatus(
                eventId,
                user.getId(),
                WaitlistStatus.WAITING
        );

        if (alreadyWaiting) {
            throw new RuntimeException("User is already on the waitlist");
        }

        Waitlist waitlist = new Waitlist();
        waitlist.setEvent(event);
        waitlist.setUser(user);
        waitlist.setStatus(WaitlistStatus.WAITING);
        waitlist.setCreatedAt(LocalDateTime.now());

        Waitlist saved = waitlistRepository.save(waitlist);

        return mapToResponse(saved);
    }

    public List<WaitlistResponse> getWaitlistByEvent(Long eventId) {
        return waitlistRepository
                .findByEventIdAndStatusOrderByCreatedAtAsc(eventId, WaitlistStatus.WAITING)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    public Optional<WaitlistResponse> promoteNextUser(Long eventId) {
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new RuntimeException("Event not found"));

        List<Waitlist> waitingUsers = waitlistRepository
                .findByEventIdAndStatusOrderByCreatedAtAsc(eventId, WaitlistStatus.WAITING);

        if (waitingUsers.isEmpty()) {
            return Optional.empty();
        }

        Waitlist first = waitingUsers.get(0);
        first.setStatus(WaitlistStatus.PROMOTED);
        first.setPromotedAt(LocalDateTime.now());

        Waitlist saved = waitlistRepository.save(first);

        emailService.sendWaitlistPromotionEmail(
                saved.getUser().getEmail(),
                event.getTitle()
        );

        return Optional.of(mapToResponse(saved));
    }

    private WaitlistResponse mapToResponse(Waitlist waitlist) {
        return WaitlistResponse.builder()
                .id(waitlist.getId())
                .eventId(waitlist.getEvent().getId())
                .userId(waitlist.getUser().getId())
                .userEmail(waitlist.getUser().getEmail())
                .status(waitlist.getStatus())
                .createdAt(waitlist.getCreatedAt())
                .promotedAt(waitlist.getPromotedAt())
                .build();
    }
}