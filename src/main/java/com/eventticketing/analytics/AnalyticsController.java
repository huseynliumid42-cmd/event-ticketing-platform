package com.eventticketing.analytics;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/analytics")
@RequiredArgsConstructor
public class AnalyticsController {

    private final AnalyticsService analyticsService;

    @PreAuthorize("hasAnyRole('ADMIN', 'ORGANIZER')")
    @GetMapping
    public ResponseEntity<AnalyticsResponse> getAnalytics() {

        return ResponseEntity.ok(
                analyticsService.getAnalytics()
        );
    }
}