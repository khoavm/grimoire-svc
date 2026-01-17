package com.service.configuration.filter;

import io.micrometer.tracing.Span;
import io.micrometer.tracing.Tracer;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
@Component
public class TracerFilter extends OncePerRequestFilter {
    private final Tracer tracer;

    public TracerFilter(Tracer tracer) {
        this.tracer = tracer;
    }


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            Span currentSpan = tracer.currentSpan();
            if (currentSpan != null) {
                response.setHeader("X-Trace-Id", currentSpan.context().traceId());
            }
            filterChain.doFilter(request, response);
        } catch (Exception e) {
            log.error("TracerFilter doFilterInternal failed {}", e.getMessage());
            throw e;
        }

    }
}
