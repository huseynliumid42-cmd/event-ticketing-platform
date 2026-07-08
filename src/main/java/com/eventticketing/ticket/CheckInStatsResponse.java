package com.eventticketing.ticket;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class CheckInStatsResponse {
    private Long checkedInCount;

    public CheckInStatsResponse(Long checkedInCount) {
        this.checkedInCount = checkedInCount;
    }
}
