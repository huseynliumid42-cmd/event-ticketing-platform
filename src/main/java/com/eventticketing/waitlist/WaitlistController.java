package com.eventticketing.waitlist;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/waitlist")
@RequiredArgsConstructor
public class WaitlistController {

    private final WaitlistService waitlistService;

    @PostMapping("/{eventId}")
    @PreAuthorize("hasRole('ATTENDEE')")
    public ResponseEntity<WaitlistResponse> joinWaitlist(
            @PathVariable Long eventId,
            Authentication authentication
    ) {
        return ResponseEntity.ok(
                waitlistService.joinWaitlist(eventId, authentication)
        );
    }

    @GetMapping("/{eventId}")
    @PreAuthorize("hasAnyRole('ADMIN','ORGANIZER')")
    public ResponseEntity<List<WaitlistResponse>> getWaitlist(
            @PathVariable Long eventId
    ) {
        return ResponseEntity.ok(
                waitlistService.getWaitlistByEvent(eventId)
        );
    }

    @PostMapping("/{eventId}/promote")
    @PreAuthorize("hasAnyRole('ADMIN','ORGANIZER')")
    public ResponseEntity<WaitlistResponse> promoteNextUser(
            @PathVariable Long eventId
    ) {
        return waitlistService.promoteNextUser(eventId)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.noContent().build());
    }
}