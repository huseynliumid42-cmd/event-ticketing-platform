package com.eventticketing.ticket;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service 
@RequiredArgsConstructor
public class CheckInLockService {

    private final StringRedisTemplate redisTemplate;

    public boolean acquireLock(String ticketCode) {

        
        String key = "checkin:lock:" + ticketCode;

        
        Boolean success = redisTemplate.opsForValue()
                .setIfAbsent(key, "LOCKED", Duration.ofSeconds(10));

        
        return Boolean.TRUE.equals(success);
    }

    public void releaseLock(String ticketCode) {

        
        String key = "checkin:lock:" + ticketCode;

        redisTemplate.delete(key);
    }
}