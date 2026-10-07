package org.zly.blogbackjava.filter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.zly.blogbackjava.common.response.ApiResponse;
import org.zly.blogbackjava.entity.WxUser;
import org.zly.blogbackjava.repository.MiniappUserRepository;
import org.zly.blogbackjava.util.JwtUtil;
import tools.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.List;

@Component
@RequiredArgsConstructor
public class MiniappAccessTokenFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    private final MiniappUserRepository miniappUserRepository;
    private final ObjectMapper objectMapper;

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        String path = request.getServletPath();
        return !path.startsWith("/miniapp/")
                || "/miniapp/login".equals(path)
                || "/miniapp/refresh".equals(path);
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {
        String authorization = request.getHeader("Authorization");
        if (authorization == null) {
            filterChain.doFilter(request, response);
            return;
        }

        if (!authorization.startsWith("Bearer ")) {
            writeUnauthorized(response, "Authorization格式错误");
            return;
        }

        String token = authorization.substring(7).trim();
        if (token.isEmpty()) {
            writeUnauthorized(response, "access token不能为空");
            return;
        }

        Claims claims;
        try {
            claims = jwtUtil.parseMiniappToken(token);
        } catch (JwtException | IllegalArgumentException ex) {
            writeUnauthorized(response, "access token无效或已过期");
            return;
        }

        if (!"miniapp".equals(claims.get("loginType", String.class))
                || !"access".equals(claims.get("type", String.class))) {
            writeUnauthorized(response, "access token无效");
            return;
        }

        String id = claims.get("id", String.class);
        String openid = claims.get("openid", String.class);
        if (id == null || openid == null) {
            writeUnauthorized(response, "access token无效");
            return;
        }

        WxUser wxUser = miniappUserRepository.findByIdAndOpenid(id, openid);
        if (wxUser == null) {
            writeUnauthorized(response, "小程序用户不存在");
            return;
        }

        var authentication = new UsernamePasswordAuthenticationToken(
                wxUser,
                null,
                List.of(new SimpleGrantedAuthority("ROLE_MINIAPP_USER"))
        );
        var securityContext = SecurityContextHolder.createEmptyContext();
        securityContext.setAuthentication(authentication);
        SecurityContextHolder.setContext(securityContext);
        filterChain.doFilter(request, response);
    }

    private void writeUnauthorized(HttpServletResponse response, String message) throws IOException {
        SecurityContextHolder.clearContext();
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding("UTF-8");
        objectMapper.writeValue(response.getOutputStream(), ApiResponse.error(401, message));
    }
}
