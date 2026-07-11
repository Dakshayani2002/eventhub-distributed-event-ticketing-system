package com.eventhub.ratelimit;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Slf4j
@Service
public class RedisRateLimiterService {

    private static final int MAX_REQUESTS = 100;
    //for testing purpose
    //private static final int MAX_REQUESTS = 3;
    private static final Duration WINDOW = Duration.ofMinutes(1);

    private final StringRedisTemplate redisTemplate;

    public RedisRateLimiterService(StringRedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public boolean isAllowed(String key) {

        Long count = redisTemplate.opsForValue().increment(key);

        log.info("Current Request Count: {}", count);

        if (count == 1) {
            redisTemplate.expire(key, Duration.ofMinutes(1));
            log.info("Expiry set for 1 minute");
        }

        log.info("Max Requests Allowed: {}", MAX_REQUESTS);

        return count <= MAX_REQUESTS;
    }
}