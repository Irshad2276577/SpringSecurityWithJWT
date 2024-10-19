package com.security.Config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.intercept.AuthorizationFilter;

@Configuration
// Configuration class for Spring Security
public class SecurityConfig {

    // Injecting the JWTRequestFilter to use for token validation
    @Autowired
    private JWTRequestFilter jwtRequestFilter;

    // Bean definition for SecurityFilterChain
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        // Disabling CORS and CSRF protection
        http.cors(c -> c.disable()).csrf(c -> c.disable());

        // Adding JWTRequestFilter before the AuthorizationFilter
        http.addFilterBefore(jwtRequestFilter, AuthorizationFilter.class);

        // Configuring request authorization rules
        http.authorizeHttpRequests(auth -> auth
                .requestMatchers("/api/user/login", "/api/user/add").permitAll() // Allow public access
                .requestMatchers("/api/user/**").hasRole("USER") // Requires USER role for user endpoints
                .requestMatchers("/api/admin/**").hasRole("ADMIN") // Requires ADMIN role for admin endpoints
                .anyRequest().authenticated() // All other requests require authentication
        );

        // Building and returning the security filter chain
        return http.build();
    }
}
