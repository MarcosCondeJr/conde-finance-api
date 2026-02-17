package com.marcoscondejr.conde_finance_api.infra.security;

import com.marcoscondejr.conde_finance_api.exception.ErrorResponse;
import com.marcoscondejr.conde_finance_api.exception.InvalidTokenException;
import com.marcoscondejr.conde_finance_api.repository.UserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import tools.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.time.OffsetDateTime;

@Component
public class SecurityFilter extends OncePerRequestFilter {

    @Autowired
    private TokenService tokenService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request, HttpServletResponse response, FilterChain filterChain
    ) throws ServletException, IOException {
        var token = this.recoverToken(request);

        try {
            if (token != null) {
                var login = this.tokenService.validateToken(token);
                UserDetails user = this.userRepository.findByLogin(login);

                if (user != null) {
                    var authentication = new UsernamePasswordAuthenticationToken(
                            user,
                            null,
                            user.getAuthorities()
                    );
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            }

            filterChain.doFilter(request, response);
        } catch (InvalidTokenException ex) {
            SecurityContextHolder.clearContext();
            writeUnauthorized(response, ex.getMessage());
        }
    }

    private String recoverToken(HttpServletRequest request) {
        var authHeader = request.getHeader("Authorization");

        if (authHeader == null) return null;

        return authHeader.replace("Bearer ", "");
    }

    private void writeUnauthorized(
            HttpServletResponse response, String message
    ) throws IOException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json;charset=UTF-8");

        ErrorResponse error = new ErrorResponse();

        error.setStatus(401);
        error.setTimestamp(OffsetDateTime.now());
        error.setTitle(message);

        response.getWriter().write(objectMapper.writeValueAsString(error));
    }
}
