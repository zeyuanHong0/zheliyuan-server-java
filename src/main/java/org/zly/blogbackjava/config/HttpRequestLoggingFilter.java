package org.zly.blogbackjava.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

@Component
@Order(Ordered.HIGHEST_PRECEDENCE + 10)
@Slf4j
public class HttpRequestLoggingFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {
        String path = request.getRequestURI();
        if (!path.startsWith("/api/") && !"/ws".equals(path)) {
            filterChain.doFilter(request, response);
            return;
        }

        long startTime = System.nanoTime();
        try {
            filterChain.doFilter(request, response);
        } finally {
            long durationMs = TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - startTime);
            int status = response.getStatus();
            if (status >= HttpServletResponse.SC_BAD_REQUEST) {
                log.warn("HTTP 请求异常：{} {}，状态码={}，耗时={}ms",
                        request.getMethod(), path, status, durationMs);
            } else {
                log.info("HTTP 请求完成：{} {}，状态码={}，耗时={}ms",
                        request.getMethod(), path, status, durationMs);
            }
        }
    }
}
