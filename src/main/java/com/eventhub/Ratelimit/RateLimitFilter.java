package com.eventhub.Ratelimit;

import com.eventhub.ratelimit.RedisRateLimiterService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
@Component
public class RateLimitFilter extends OncePerRequestFilter {

    private final RedisRateLimiterService rateLimiterService;

    public RateLimitFilter(RedisRateLimiterService rateLimiterService) {
        this.rateLimiterService = rateLimiterService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        log.info("========== RATE LIMIT FILTER ==========");
        log.info("Request URI: {}", request.getRequestURI());

        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.isAuthenticated()) {

            String email = authentication.getName();

            log.info("Authenticated User: {}", email);

            String key = "rate_limit:" + email;

            log.info("Redis Key: {}", key);

            boolean allowed = rateLimiterService.isAllowed(key);

            log.info("Request Allowed: {}", allowed);

            if (!allowed) {

                log.warn("Rate limit exceeded for user: {}", email);

                response.setStatus(HttpStatus.TOO_MANY_REQUESTS.value());
                response.getWriter().write("Rate limit exceeded. Try again later.");
                return;
            }
        }

        filterChain.doFilter(request, response);
    }
}