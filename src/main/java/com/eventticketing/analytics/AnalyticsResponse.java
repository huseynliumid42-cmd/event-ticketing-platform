package com.eventticketing.analytics;

import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
@Builder
public class AnalyticsResponse {

    
    private Long ticketsSold;

    
    private Long checkedInCount;

    
    private BigDecimal totalRevenue;

    
    private Double attendanceRate;
}