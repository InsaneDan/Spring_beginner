package ru.geekbrains.security;

import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Base64;

public class CustomFilter extends OncePerRequestFilter {
    private final AuthenticationManager authenticationManager;

    public CustomFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (authHeader != null) {
            authHeader = authHeader.replace("Basic ", "");
            String decode = new String(Base64.getDecoder().decode(authHeader));
            String[] split = decode.split(":");
            String login = split[0];
            String password = split[1];
            Authentication authentication = new UsernamePasswordAuthenticationToken(login, password);
            authentication = authenticationManager.authenticate(authentication);

            if (authentication.isAuthenticated()) {
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }
        filterChain.doFilter(request, response);
    }
}
