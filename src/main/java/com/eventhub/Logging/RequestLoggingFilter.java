package com.eventhub.logging;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.UUID;

@Slf4j
@Component
public class RequestLoggingFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        String requestId = UUID.randomUUID()
                .toString()
                .substring(0, 8);

        long startTime = System.currentTimeMillis();

        MDC.put("requestId", requestId);

        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        String user = authentication != null
                ? authentication.getName()
                : null;

        log.info("========== Incoming Request ==========");
        log.info("Request ID : {}", requestId);
        log.info("User       : {}", user);
        log.info("Method     : {}", request.getMethod());
        log.info("URI        : {}", request.getRequestURI());

        try {
            filterChain.doFilter(request, response);
        } finally {

            long executionTime = System.currentTimeMillis() - startTime;

            log.info("========== Request Completed ==========");
            log.info("Request ID      : {}", requestId);
            log.info("Response Status : {}", response.getStatus());
            log.info("Execution Time  : {} ms", executionTime);

            MDC.clear();
        }
    }
}