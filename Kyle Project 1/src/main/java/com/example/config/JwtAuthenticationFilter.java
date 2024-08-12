package com.example.config;

import com.example.dto.user.UserDto;
import com.example.response.ErrorResponse;
import com.example.util.JwtUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.*;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
        if ("/api/1.0/user/profile".equals(request.getRequestURI()) || "/api/1.0/order/checkout".equals(request.getRequestURI())) {
            String header = request.getHeader("Authorization");
            String token = (header != null && header.startsWith("Bearer ")) ? header.substring(7) : null;
            if (token == null) {
                sendErrorResponse(response, HttpServletResponse.SC_UNAUTHORIZED, "No token found");
                return;
            } else if (!jwtUtil.isTokenValid(token)) {
                sendErrorResponse(response, HttpServletResponse.SC_FORBIDDEN, "Invalid token or token is expired");
                return;
            } else {
                Map<String, Object> map = jwtUtil.getClaims(token);
                UserDto userDto = new UserDto();
                userDto.setProvider((String) map.get("provider"));
                userDto.setName((String) map.get("name"));
                userDto.setEmail((String) map.get("email"));
                userDto.setPicture((String) map.get("picture"));
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDto, null, new ArrayList<GrantedAuthority>());
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }
        chain.doFilter(request, response);
    }

    private void sendErrorResponse(HttpServletResponse response, int status, String message) throws IOException {
        response.setStatus(status);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(objectMapper.writeValueAsString(new ErrorResponse(message)));
    }

}



