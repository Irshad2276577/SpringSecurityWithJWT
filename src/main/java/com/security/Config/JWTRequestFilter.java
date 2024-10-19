package com.security.Config;

import com.security.Entity.Customer;
import com.security.Repository.CustomerRepository;
import com.security.Service.JWTService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;
import java.util.Optional;

@Component
// JWTRequestFilter is a filter that intercepts HTTP requests to validate JWT tokens
public class JWTRequestFilter extends OncePerRequestFilter {

    // Injecting CustomerRepository to access customer data
    @Autowired
    private CustomerRepository customerRepository;

    // Injecting JWTService to handle JWT operations
    @Autowired
    private JWTService jwtService;

    @Override
    // Method to filter incoming requests
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // Retrieve the Authorization header from the request
        String tokenHeader = request.getHeader("Authorization");

        // Check if the header is present and starts with "Bearer "
        if (tokenHeader != null && tokenHeader.startsWith("Bearer ")) {
            // Extract the token from the header
            String token = tokenHeader.substring(7);

            // Get the username from the token using JWTService
            Optional<Customer> customer = customerRepository.findByEmail(jwtService.getUsername(token));

            // If a customer is found based on the username
            if (customer.isPresent()) {
                // Create an authentication token with the customer's role
                UsernamePasswordAuthenticationToken authenticationToken =
                        new UsernamePasswordAuthenticationToken(customer, null, Collections.singleton(new SimpleGrantedAuthority(customer.get().getRole())));

                // Set additional details for the authentication
                authenticationToken.setDetails(new WebAuthenticationDetails(request));

                // Set the authentication object in the SecurityContext
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }
        }

        // Continue the filter chain
        filterChain.doFilter(request, response);
    }
}
