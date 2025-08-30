package com.egronx.furniturehome.security;

import com.egronx.furniturehome.service.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws ServletException, IOException {
        
        // Skip JWT processing for public endpoints
        String requestURI = request.getRequestURI();
        if (requestURI.startsWith("/api/auth/signup") || 
            requestURI.startsWith("/api/auth/login") || 
            requestURI.startsWith("/api/auth/reset-password")) {
            filterChain.doFilter(request, response);
            return;
        }
        
        // Get the Authorization header
        final String authHeader = request.getHeader("Authorization");
        final String jwt;
        final String userEmail;
        
        // Check if Authorization header exists and starts with "Bearer "
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }
        
        // Extract JWT token (remove "Bearer " prefix)
        jwt = authHeader.substring(7);
        
        try {
            // Extract user email from JWT token
            userEmail = jwtService.extractUsername(jwt);
            
            // If user email is found and no authentication exists in context
            if (userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                
                // Load user details from database
                UserDetails userDetails = this.userDetailsService.loadUserByUsername(userEmail);
                
                // Validate JWT token
                if (jwtService.isTokenValid(jwt, userDetails)) {
                    
                    // Create authentication token
                    UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                            userDetails,
                            null,
                            userDetails.getAuthorities()
                    );
                    
                    // Set authentication details
                    authToken.setDetails(
                            new WebAuthenticationDetailsSource().buildDetails(request)
                    );
                    
                    // Set authentication in security context
                    SecurityContextHolder.getContext().setAuthentication(authToken);
                }
            }
        } catch (Exception e) {
            // Log the exception but don't throw it to avoid breaking the filter chain
            logger.error("Error processing JWT token", e);
        }
        
        // Continue with the filter chain
        filterChain.doFilter(request, response);
    }
} 