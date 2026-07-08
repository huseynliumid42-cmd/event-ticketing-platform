package com.eventticketing.ticket;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class TicketTierController {

    private final TicketTierService ticketTierService;

    @PreAuthorize("hasAnyRole('ADMIN', 'ORGANIZER')")
    @PostMapping("/events/{eventId}/ticket-tiers")
    public ResponseEntity<TicketTierResponse> createTicketTier(
            @PathVariable Long eventId,
            @RequestBody TicketTierRequest request
    ) {
        return ResponseEntity.ok(ticketTierService.createTicketTier(eventId, request));
    }

    @GetMapping("/events/{eventId}/ticket-tiers")
    public ResponseEntity<List<TicketTierResponse>> getTicketTiersByEvent(
            @PathVariable Long eventId
    ) {
        return ResponseEntity.ok(ticketTierService.getTicketTiersByEvent(eventId));
    }

    @GetMapping("/ticket-tiers/{id}")
    public ResponseEntity<TicketTierResponse> getTicketTierById(
            @PathVariable Long id
    ) {
        return ResponseEntity.ok(ticketTierService.getTicketTierById(id));
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'ORGANIZER')")
    @PutMapping("/ticket-tiers/{id}")
    public ResponseEntity<TicketTierResponse> updateTicketTier(
            @PathVariable Long id,
            @RequestBody TicketTierRequest request
    ) {
        return ResponseEntity.ok(ticketTierService.updateTicketTier(id, request));
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'ORGANIZER')")
    @DeleteMapping("/ticket-tiers/{id}")
    public ResponseEntity<Void> deleteTicketTier(
            @PathVariable Long id
    ) {
        ticketTierService.deleteTicketTier(id);
        return ResponseEntity.noContent().build();
    }
}