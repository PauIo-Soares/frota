package br.edu.fateczl.frota.security;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class ApiKeyFilter implements Filter {

    private final StringRedisTemplate redis;

    public ApiKeyFilter(StringRedisTemplate redis) {
        this.redis = redis;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;

        String apiKey = req.getHeader("API-Key");

        if (apiKey == null || apiKey.isBlank()) {
            res.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        String status = redis.opsForValue().get("api_key:" + apiKey);

        if (!"ativo".equalsIgnoreCase(status)) {
            res.setStatus(HttpServletResponse.SC_FORBIDDEN);
            return;
        }
        chain.doFilter(request, response);
    }

}