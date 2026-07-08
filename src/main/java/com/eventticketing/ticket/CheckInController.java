package com.eventticketing.ticket;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController 
@RequestMapping("/api/check-in")
@RequiredArgsConstructor
public class CheckInController {

    private final CheckInService checkInService;

    @PreAuthorize("hasAnyRole('STAFF', 'ADMIN', 'ORGANIZER')")
    @PostMapping("/{ticketCode}")
    public ResponseEntity<CheckInResponse> checkIn(
            @PathVariable String ticketCode
    ) {
        
        return ResponseEntity.ok(
                checkInService.checkIn(ticketCode)
        );
    }
}