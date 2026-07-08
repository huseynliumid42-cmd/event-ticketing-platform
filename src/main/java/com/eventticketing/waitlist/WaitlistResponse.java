package com.eventticketing.waitlist;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class WaitlistResponse {

    private Long id;
    private Long eventId;
    private Long userId;
    private String userEmail;
    private WaitlistStatus status;
    private LocalDateTime createdAt;
    private LocalDateTime promotedAt;
}