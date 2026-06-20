package com.eventhub.service;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class BookingLockService {

    private final RedisTemplate<String, Object> redisTemplate;

    public BookingLockService(
            RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public boolean acquireLock(Long eventId) {

        String key = "booking_lock:event:" + eventId;

        Boolean success =
                redisTemplate.opsForValue()
                        .setIfAbsent(
                                key,
                                "LOCKED",
                                10,
                                TimeUnit.SECONDS
                        );

        return Boolean.TRUE.equals(success);
    }

    public void releaseLock(Long eventId) {

        String key = "booking_lock:event:" + eventId;
        redisTemplate.delete(key);
    }
}